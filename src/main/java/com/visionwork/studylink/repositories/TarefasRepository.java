package com.visionwork.studylink.repositories;

import com.visionwork.studylink.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TarefasRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByDataFimBetween(LocalDate dataInicio, LocalDate dataFim);

}
