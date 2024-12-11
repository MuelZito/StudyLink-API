package com.visionwork.studylink.controllers;

import com.visionwork.studylink.dto.usuario.insert.UserLoginDTO;
import com.visionwork.studylink.dto.usuario.insert.UserRegisterDTO;
import com.visionwork.studylink.dto.usuario.read.ReponseDTO;
import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import com.visionwork.studylink.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UsuarioRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        Usuario usuario = this.repository.findByEmail(userLoginDTO.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(userLoginDTO.senha(), usuario.getSenha())) {
            String token = this.tokenService.gerarToken(usuario);

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();



            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(new ReponseDTO(usuario.getNomeUsuario(), usuario.getEmail(), token, usuario.getImagemPerfil()));
        }

        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRegisterDTO userRegisterDTO) {
        Optional<Usuario> usuario = this.repository.findByEmail(userRegisterDTO.email());
        if (usuario.isEmpty()) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setSenha(passwordEncoder.encode(userRegisterDTO.senha()));
            novoUsuario.setEmail(userRegisterDTO.email());
            novoUsuario.setNomeUsuario(userRegisterDTO.nome_usuario());
            this.repository.save(novoUsuario);

            String token = this.tokenService.gerarToken(novoUsuario);

            ResponseCookie cookie = ResponseCookie.from("token", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(new ReponseDTO(novoUsuario.getNomeUsuario(), novoUsuario.getEmail(), token , novoUsuario.getImagemPerfil()));


        }
        return ResponseEntity.badRequest().build();
    }

}