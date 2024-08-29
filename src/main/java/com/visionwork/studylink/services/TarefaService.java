package com.visionwork.studylink.services;


import com.visionwork.studylink.dto.TarefaDTO;
import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.repositories.TarefasRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    TarefasRepository tarefasRepository;

    @Transactional
    public TarefaDTO salvarTarefa(Tarefa tarefa) {
        Tarefa tarefa1 = tarefasRepository.save(tarefa);
        TarefaDTO dto = new TarefaDTO(tarefa1);
        return dto;
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

}
