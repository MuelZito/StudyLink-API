package com.visionwork.studylink.services;


import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.repositories.TarefasRepository;
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
    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefasRepository.save(tarefa);
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
    public Tarefa alterarTarefa(Tarefa tarefa) {
        Tarefa tarefaNova = tarefasRepository.findById(tarefa.getId()).orElseThrow(() -> new IllegalArgumentException("id nao existe"));
        tarefaNova.update(tarefa);
        return tarefaNova;
    }

    @Transactional
    public List<Tarefa> listaTarefas(){
        return null;
    }


}
