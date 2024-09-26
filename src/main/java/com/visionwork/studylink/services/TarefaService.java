package com.visionwork.studylink.services;


import com.visionwork.studylink.dto.tarefa.insert.TarefaCreateDTO;
import com.visionwork.studylink.dto.tarefa.insert.TarefaUpdateDTO;
import com.visionwork.studylink.dto.tarefa.read.TarefaReadDTO;
import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.entities.Usuario;
import com.visionwork.studylink.repositories.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
                .prioridade(tarefaCreateDTO.prioridade())
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
    public TarefaReadDTO alterarTarefa(Long id, TarefaUpdateDTO tarefaUpdateDTO) {
        // Obtém o usuário autenticado
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Busca a tarefa pelo ID e pelo usuário autenticado
        Tarefa tarefa = tarefasRepository.findByIdAndUsuario(id, principal)
                .orElseThrow(() -> new AccessDeniedException("Você não tem permissão para atualizar esta tarefa."));

        // Atualiza os campos da tarefa existente
        tarefa.setTitulo(tarefaUpdateDTO.titulo());
        tarefa.setDescricao(tarefaUpdateDTO.descricao());
        tarefa.setDataInicio(tarefaUpdateDTO.dataInicio());
        tarefa.setDataFim(tarefaUpdateDTO.dataFim());
        tarefa.setPrioridade(tarefaUpdateDTO.prioridade());
        tarefa.setStatus(tarefaUpdateDTO.status());

        // Salva a tarefa atualizada no banco de dados
        tarefa = tarefasRepository.save(tarefa); // O save aqui é opcional se você estiver usando @Transactional

        return new TarefaReadDTO(tarefa);
    }

    @Transactional(readOnly = true)
    public List<TarefaReadDTO> buscarTarefas(LocalDate dataInicio, LocalDate dataFim) {
        Usuario principal = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Tarefa> tarefas = tarefasRepository.findByUsuarioAndDataFimBetween(principal, dataInicio, dataFim);
        return tarefas.stream().map(tarefa -> new TarefaReadDTO(tarefa)).collect(Collectors.toList());
    }


}
