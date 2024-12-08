package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.arquivo.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    List<Arquivo> findByAnotacaoId(Long anotacaoId);

}