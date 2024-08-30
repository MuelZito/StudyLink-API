package com.visionwork.studylink.services;


import com.visionwork.studylink.dto.TarefaDTO;
import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.repositories.TarefasRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    TarefasRepository tarefasRepository;

    @Transactional
    public TarefaDTO salvarTarefa(Tarefa tarefa) {
        Tarefa salvarTarefa = tarefasRepository.save(tarefa);
        return new TarefaDTO(salvarTarefa);
    }

    @Transactional
    public void deletarById(Long id) {
        if (tarefasRepository.existsById(id)) {
            tarefasRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tarefa com o  ID " + id + " não foi possivel deletar");
        }
    }

    @Transactional
    public TarefaDTO alterarTarefa(Tarefa tarefa) {
        Tarefa tarefaAtualizada = tarefasRepository.findById(tarefa.getId()).orElseThrow(() -> new IllegalArgumentException("ID não existe"));
        tarefaAtualizada.update(tarefa);
        return new TarefaDTO(tarefaAtualizada);
    }

    @Transactional
    public List<TarefaDTO> buscarTarefas(LocalDate dataInicio, LocalDate dataFim) {
        List<Tarefa> tarefas = tarefasRepository.findByDataFimBetween(dataInicio, dataFim);
        return tarefas.stream().map(tarefa -> new TarefaDTO(tarefa)).collect(Collectors.toList());
    }


}
