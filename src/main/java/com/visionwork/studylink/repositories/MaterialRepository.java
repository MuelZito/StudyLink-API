package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.material.Anotacao;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.tarefa.Tarefa;
import com.visionwork.studylink.models.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByIdAndUsuario(Long id, Usuario usuario);
}
