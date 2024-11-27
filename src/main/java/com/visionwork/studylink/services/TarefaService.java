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
    public TarefaReadDTO alterarTarefa(Long id, TarefaUpdateDTO tarefaUpdateDTO, boolean editarRecorrenciaInteira) {
        // Obter o usuário principal do contexto de segurança
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar a tarefa original associada ao usuário
        Tarefa tarefaOriginal = tarefasRepository.findByIdAndUsuario(id, principal)
                .orElseThrow(() -> new AccessDeniedException("Você não tem permissão para modificar esta tarefa"));

        // Verificar se a tarefa é parte de uma recorrência
        if (tarefaOriginal.getRecurrenceID() != null) {
            if (editarRecorrenciaInteira) {
                // Encontrar todas as tarefas associadas ao mesmo RecurrenceID
                List<Tarefa> tarefasRecorrentes = tarefasRepository.findByRecurrenceID(tarefaOriginal.getRecurrenceID());

                if (tarefasRecorrentes.isEmpty()) {
                    throw new NoSuchElementException("Nenhuma tarefa recorrente encontrada para o ID de recorrência fornecido.");
                }

                // Atualizar todas as tarefas recorrentes
                for (Tarefa tarefa : tarefasRecorrentes) {
                    // Atualizar regra de recorrência (se aplicável)
                    if (tarefa.getRecurrenceRule() == null || !tarefa.getRecurrenceRule().equals(tarefaUpdateDTO.recurrenceRule())) {
                        tarefa.setRecurrenceRule(tarefaUpdateDTO.recurrenceRule());
                    }
                    atualizarTarefa(tarefa, tarefaUpdateDTO);
                }

                // Salvar alterações
                tarefasRepository.saveAll(tarefasRecorrentes);
            } else {
                // Criar uma nova ocorrência única na série
                Tarefa novaTarefa = new Tarefa();
                novaTarefa.update(tarefaUpdateDTO);
                novaTarefa.setRecurrenceID(tarefaOriginal.getRecurrenceID());

                // Atualizar RecurrenceException da tarefa original
                String novaExcecao = tarefaOriginal.getDataInicio().toString();
                if (tarefaOriginal.getRecurrenceException() != null) {
                    novaExcecao = tarefaOriginal.getRecurrenceException() + "," + novaExcecao;
                }
                tarefaOriginal.setRecurrenceException(novaExcecao);

                // Salvar alterações
                tarefasRepository.save(novaTarefa);
                tarefasRepository.save(tarefaOriginal);

                return new TarefaReadDTO(novaTarefa);
            }
        } else {
            // Atualizar tarefa única
            atualizarTarefa(tarefaOriginal, tarefaUpdateDTO);
        }

        // Salvar alterações na tarefa original e retornar DTO atualizado
        tarefasRepository.save(tarefaOriginal);
        return new TarefaReadDTO(tarefaOriginal);
    }

    private void atualizarTarefa(Tarefa tarefa, TarefaUpdateDTO tarefaUpdateDTO) {
        // Aplicar atualizações na tarefa a partir do DTO
        tarefa.update(tarefaUpdateDTO);
        tarefasRepository.save(tarefa);
    }






    @Transactional(readOnly = true)
    public List<TarefaReadDTO> buscarTarefas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Tarefa> tarefas = tarefasRepository.findByUsuarioAndDataFimBetween(principal, dataInicio, dataFim);
        return tarefas.stream().map(tarefa -> new TarefaReadDTO(tarefa)).collect(Collectors.toList());
    }


}
