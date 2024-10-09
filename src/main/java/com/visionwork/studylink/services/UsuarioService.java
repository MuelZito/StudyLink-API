package com.visionwork.studylink.services;

import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Optional<Usuario> findByIdUsuario(Long id){
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


}
