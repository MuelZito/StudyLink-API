    package com.visionwork.studylink.email;


    import com.visionwork.studylink.models.usuario.Usuario;
    import com.visionwork.studylink.security.TokenService;
    import com.visionwork.studylink.services.UsuarioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.time.Instant;

    @RestController
    @RequestMapping("/api/password-reset")
    public class PasswordResetController {

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private TokenService tokenService;

        @Autowired
        private EmailService emailService;

        @PostMapping("/request")
        public ResponseEntity<?> solicitarRedefinicaoSenha(@RequestBody PasswordResetRequest request) {
            Usuario usuario = usuarioService.findUserByEmail(request.getEmail());
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }

            String token = tokenService.gerarTokenRedefinicaoSenha(usuario.getEmail());
            emailService.enviarEmailRedefinicaoSenha(usuario.getEmail(), token);

            return ResponseEntity.ok("E-mail de redefinição de senha enviado.");
        }

        @PostMapping("/reset")
        public ResponseEntity<?> redefinirSenha(@RequestParam String token, @RequestBody PasswordResetRequest request) {
            System.out.println("Token recebido: " + token);
            System.out.println("Hora atual UTC: " + Instant.now());
            System.out.println("Nova senha recebida: " + request.getNewPassword());

            try {
                String email = tokenService.validarToken(token);
                System.out.println("E-mail decodificado do token: " + email);
                Usuario usuario = usuarioService.findUserByEmail(email);

                if (usuario == null) {
                    System.out.println("Usuário não encontrado para o e-mail: " + email);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
                }
                System.out.println("Atualizando senha para o usuário: " + usuario.getEmail());
                usuarioService.updatePassword(usuario, request.getNewPassword());
                System.out.println("Senha atualizada com sucesso.");

                return ResponseEntity.ok("Senha redefinida com sucesso.");
            } catch (TokenService.TokenException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
            }
        }

    }

