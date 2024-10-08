package com.visionwork.studylink.controllers;

import com.visionwork.studylink.dto.usuario.insert.UserLoginDTO;
import com.visionwork.studylink.dto.usuario.insert.UserRegisterDTO;
import com.visionwork.studylink.dto.usuario.read.ReponseDTO;
import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import com.visionwork.studylink.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UsuarioRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    // Método para login utilizando POST
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        Usuario usuario = this.repository.findByEmail(userLoginDTO.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(userLoginDTO.senha(), usuario.getSenha())) {
            String token = this.tokenService.geracaoToke(usuario);
            return ResponseEntity.ok(new ReponseDTO(usuario.getNomeUsuario(), token));
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

            String token = this.tokenService.geracaoToke(novoUsuario);
            return ResponseEntity.ok(new ReponseDTO(novoUsuario.getNomeUsuario(), token));

        }
        return ResponseEntity.badRequest().build();
    }
}
