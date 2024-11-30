package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.anotacao.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {
    List<Anotacao> findByTituloContainingIgnoreCase(String titulo);

    List<Anotacao> findByMaterialId(Long materialId);
}