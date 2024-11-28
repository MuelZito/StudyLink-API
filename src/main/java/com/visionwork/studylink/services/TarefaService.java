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
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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

        // Atualizar a tarefa pai com a nova recurrenceException
        String updatedRecurrenceException = getRecurrenceExceptionString(tarefaOriginal.getRecurrenceException(), tarefaUpdateDTO.dataInicio());
        tarefaOriginal.setRecurrenceException(updatedRecurrenceException);
        tarefasRepository.save(tarefaOriginal);

        // Criar uma nova tarefa para a ocorrência única
        Tarefa novaTarefa = new Tarefa.Builder()
                .titulo(tarefaUpdateDTO.titulo())
                .descricao(tarefaUpdateDTO.descricao())
                .dataInicio(tarefaUpdateDTO.dataInicio())
                .dataFim(tarefaUpdateDTO.dataFim())
                .recurrenceID(tarefaOriginal.getId())
                .usuario(principal)
                .build();

        // Salvar a nova tarefa
        Tarefa tarefaSalva = tarefasRepository.save(novaTarefa);

        return new TarefaReadDTO(tarefaSalva);
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

        // Buscar todas as tarefas no intervalo de datas
        List<Tarefa> tarefas = tarefasRepository.findByUsuarioAndDataFimBetween(principal, dataInicio, dataFim);

        // Filtrar tarefas
        List<Tarefa> tarefasFiltradas = tarefas.stream()
                .filter(tarefa -> {
                    // Se a tarefa tem RecurrenceID (é uma ocorrência de uma tarefa recorrente)
                    if (tarefa.getRecurrenceID() != null) {
                        // Se tem exceções
                        if (tarefa.getRecurrenceException() != null && !tarefa.getRecurrenceException().isEmpty()) {
                            // Converte a data da tarefa para string no mesmo formato da exceção
                            String dataInicioStr = tarefa.getDataInicio().toString();

                            // Verifica se esta ocorrência específica está na lista de exceções
                            return !Arrays.stream(tarefa.getRecurrenceException().split(","))
                                    .anyMatch(excecao -> excecao.equals(dataInicioStr));
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        // Mapear para o DTO de leitura
        return tarefasFiltradas.stream()
                .map(TarefaReadDTO::new)
                .collect(Collectors.toList());
    }


}
