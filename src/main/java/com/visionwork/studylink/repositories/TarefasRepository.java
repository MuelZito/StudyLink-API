package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.tarefa.Tarefa;
import com.visionwork.studylink.models.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TarefasRepository extends JpaRepository<Tarefa, Long> {

    // Buscar tarefas por usuário e intervalo de tempo
    List<Tarefa> findByUsuarioAndDataFimBetween(Usuario usuario, LocalDateTime dataInicio, LocalDateTime dataFim);

    // Buscar tarefa específica por ID e usuário
    Optional<Tarefa> findByIdAndUsuario(Long id, Usuario usuario);

    // Buscar todas as tarefas de uma série pela RecurrenceID
    List<Tarefa> findByRecurrenceID(Long recurrenceID);

    Optional<Tarefa> findByIdAndRecurrenceIDAndUsuario(Long id, Long recurrenceID, Usuario usuario);


    // Buscar tarefas que possuem um RecurrenceException específico


}
