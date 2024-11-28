package com.visionwork.studylink.services;


import com.visionwork.studylink.dto.tarefa.insert.TarefaCreateDTO;
import com.visionwork.studylink.dto.tarefa.insert.TarefaUpdateDTO;
import com.visionwork.studylink.dto.tarefa.read.TarefaReadDTO;
import com.visionwork.studylink.models.tarefa.Tarefa;
import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.TarefasRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    TarefasRepository tarefasRepository;

    @Transactional
    public TarefaReadDTO criarTarefa(TarefaCreateDTO tarefaCreateDTO) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tarefa tarefa = new Tarefa.Builder()
                .titulo(tarefaCreateDTO.titulo())
                .descricao(tarefaCreateDTO.descricao())
                .dataInicio(tarefaCreateDTO.dataInicio())
                .dataFim(tarefaCreateDTO.dataFim())
                .recurrenceRule(tarefaCreateDTO.recurrenceRule())
                .recurrenceID(tarefaCreateDTO.recurrenceID())
                .recurrenceException(tarefaCreateDTO.recurrenceException())
                .usuario(principal)
                .build();
        tarefa = tarefasRepository.save(tarefa);
        return new TarefaReadDTO(tarefa);
    }

    @Transactional
    public void deletarById(Long id) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tarefa tarefa = tarefasRepository.findByIdAndUsuario(id, principal).
                orElseThrow(() -> new AccessDeniedException("Você não tem permissão pra deletar essa tarefa"));
        tarefasRepository.delete(tarefa);
    }

    @Transactional
    public TarefaReadDTO alterarOcorrenciaUnica(Long id, TarefaUpdateDTO tarefaUpdateDTO) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar tarefa original usando o recurrenceID
        Tarefa tarefaOriginal = tarefasRepository.findByIdAndUsuario(tarefaUpdateDTO.recurrenceID(), principal)
                .orElseThrow(() -> new AccessDeniedException("Tarefa não encontrada ou sem permissão"));

        // Criar uma nova tarefa com as atualizações
        Tarefa novaTarefa = new Tarefa.Builder()
                .id(id)
                .titulo(tarefaUpdateDTO.titulo())
                .descricao(tarefaUpdateDTO.descricao())
                .dataInicio(tarefaUpdateDTO.dataInicio())
                .dataFim(tarefaUpdateDTO.dataFim())
                .recurrenceID(tarefaOriginal.getId())
                .recurrenceException(getRecurrenceExceptionString(tarefaOriginal.getRecurrenceException(), tarefaUpdateDTO.dataInicio()))
                .usuario(principal)
                .build();

        // Salvar a nova tarefa
        Tarefa atualizadaTarefa = tarefasRepository.save(novaTarefa);

        return new TarefaReadDTO(atualizadaTarefa);
    }

    private String getRecurrenceExceptionString(String existingExceptions, LocalDateTime newExceptionDate) {
        if (existingExceptions == null || existingExceptions.isEmpty()) {
            return newExceptionDate.toString();
        } else if (!existingExceptions.contains(newExceptionDate.toString())) {
            return existingExceptions + "," + newExceptionDate.toString();
        } else {
            throw new IllegalArgumentException("Ocorrência já editada previamente.");
        }
    }



    @Transactional
    public TarefaReadDTO alterarTarefa(Long id, TarefaUpdateDTO tarefaUpdateDTO, boolean editarRecorrenciaInteira) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar a tarefa original associada ao usuário
        Tarefa tarefaOriginal = tarefasRepository.findByIdAndUsuario(id, principal)
                .orElseThrow(() -> new AccessDeniedException("Você não tem permissão para modificar esta tarefa"));

        // Caso seja uma série de tarefas recorrentes
        if (tarefaOriginal.getRecurrenceID() != null && editarRecorrenciaInteira) {
            // Buscar todas as tarefas recorrentes pela mesma ID de recorrência
            List<Tarefa> tarefasRecorrentes = tarefasRepository.findByRecurrenceID(tarefaOriginal.getRecurrenceID());

            if (tarefasRecorrentes.isEmpty()) {
                throw new NoSuchElementException("Nenhuma tarefa recorrente encontrada para o ID de recorrência fornecido.");
            }

            // Atualizar cada tarefa da série
            for (Tarefa tarefa : tarefasRecorrentes) {
                tarefa.update(tarefaUpdateDTO);

            }

            tarefasRepository.saveAll(tarefasRecorrentes);
        } else if (tarefaOriginal.getRecurrenceID() == null) {
            // Caso a tarefa não seja recorrente, atualize normalmente
            tarefaOriginal.update(tarefaUpdateDTO);

        }

        // Persistir e retornar a tarefa atualizada
        tarefasRepository.save(tarefaOriginal);
        return new TarefaReadDTO(tarefaOriginal);
    }







    @Transactional(readOnly = true)
    public List<TarefaReadDTO> buscarTarefas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Tarefa> tarefas = tarefasRepository.findByUsuarioAndDataFimBetween(principal, dataInicio, dataFim);
        return tarefas.stream().map(tarefa -> new TarefaReadDTO(tarefa)).collect(Collectors.toList());
    }


}
