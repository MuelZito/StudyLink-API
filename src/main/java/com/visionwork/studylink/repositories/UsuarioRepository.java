package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<String> findImagemPerfilById(@Param("id") Long id);
}
