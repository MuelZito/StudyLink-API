package com.visionwork.studylink.controllers;


import com.visionwork.studylink.dto.TarefaDTO;
import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.repositories.TarefasRepository;
import com.visionwork.studylink.services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TarefasController {

    @Autowired
    TarefaService tarefaService;

    @PostMapping(value = "/tarefas")
    public ResponseEntity<TarefaDTO> inserirTarefa(@RequestBody Tarefa tarefa) {
        TarefaDTO salvarTarefa = tarefaService.salvarTarefa(tarefa);
        return ResponseEntity.ok(salvarTarefa);
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<String> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarById(id);
        return ResponseEntity.ok("Tarefa deletada com sucesso.");
    }

    @PutMapping(value = "/tarefas")
    public ResponseEntity<TarefaDTO> alterarTarefa(@RequestBody Tarefa tarefa){
        TarefaDTO tarefaAtualizada = tarefaService.alterarTarefa(tarefa);
        return ResponseEntity.ok(tarefaAtualizada);
    }

}
