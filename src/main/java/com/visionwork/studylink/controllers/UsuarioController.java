package com.visionwork.studylink.controllers;


import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.entities.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import com.visionwork.studylink.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;



    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findByIdUsuario(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }




}
