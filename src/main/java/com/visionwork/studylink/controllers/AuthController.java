package com.visionwork.studylink.controllers;

import com.visionwork.studylink.dto.LoginResquestDTO;
import com.visionwork.studylink.dto.RegisterRequestDTO;
import com.visionwork.studylink.dto.ResposnseDTO;
import com.visionwork.studylink.entities.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import com.visionwork.studylink.security.TokenService;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginResquestDTO body) {
        Usuario usuario = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        if (passwordEncoder.matches(body.senha(),usuario.getSenha())) {
            String token = this.tokenService.geracaoToke(usuario);
            return ResponseEntity.ok(new ResposnseDTO(usuario.getNome_usuario(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity login(@RequestBody RegisterRequestDTO body) {
        Optional<Usuario> usuario = this.repository.findByEmail(body.email());
        if (usuario.isEmpty()) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setSenha(passwordEncoder.encode(body.senha()));
            novoUsuario.setEmail(body.email());
            novoUsuario.setNome_usuario(body.nome_usuario());
            this.repository.save(novoUsuario);

            String token = this.tokenService.geracaoToke(novoUsuario);
            return ResponseEntity.ok(new ResposnseDTO(novoUsuario.getNome_usuario(), token));

        }
        return ResponseEntity.badRequest().build();
    }
}
