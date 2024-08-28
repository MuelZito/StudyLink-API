package com.visionwork.studylink.repositories;

import com.visionwork.studylink.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefasRepository extends JpaRepository<Tarefa, Long> {
}
