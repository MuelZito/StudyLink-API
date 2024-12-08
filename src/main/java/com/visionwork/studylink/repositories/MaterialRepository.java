package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.anotacao.Anotacao;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.material.Visibilidade;
import com.visionwork.studylink.models.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Material> findByIdAndUsuario(Long id, Usuario usuario);
    List<Material> findByTituloContainingIgnoreCase(String titulo);
    List<Material> findByUsuario(Usuario usuario);
    List<Material> findByVisibilidade(Visibilidade visibilidade);
    List<Material> findByTituloContainingIgnoreCaseOrAreaConhecimentoContainingIgnoreCase(
            String titulo,
            String areaConhecimento
    );
}
