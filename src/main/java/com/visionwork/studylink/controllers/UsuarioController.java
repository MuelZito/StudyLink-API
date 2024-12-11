package com.visionwork.studylink.controllers;


import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findByIdUsuario(id);
        if (usuario.isPresent()) {
            System.out.println("Imagem Base64: " + usuario.get().getImagemPerfil());
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }



    @PutMapping("/login/usuarios/imagem-perfil")
    public ResponseEntity<?> atualizarImagemPerfil(@RequestBody String imagemBase64) {
        try {
            usuarioService.atualizarImagemPerfil(imagemBase64);
            return ResponseEntity.ok().body(Map.of("message", "Imagem de perfil atualizada com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

}
