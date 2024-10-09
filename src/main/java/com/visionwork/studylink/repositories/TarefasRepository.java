package com.visionwork.studylink.repositories;

import com.visionwork.studylink.models.tarefa.Tarefa;
import com.visionwork.studylink.models.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TarefasRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUsuarioAndDataFimBetween(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);
    Optional<Tarefa> findByIdAndUsuario(Long id, Usuario usuario);


}
