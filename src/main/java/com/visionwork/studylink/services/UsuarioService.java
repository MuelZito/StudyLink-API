package com.visionwork.studylink.services;

import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<Usuario> findByIdUsuario(Long id){
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario findUserByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }


    @Transactional
    public void updatePassword(Usuario usuario, String newPassword) {
        usuario.setSenha(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarImagemPerfil(String imagemBase64) {
        String email = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        usuario.setImagemPerfil(imagemBase64);
        usuarioRepository.save(usuario);
    }


}
