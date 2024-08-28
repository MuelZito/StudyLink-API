package com.visionwork.studylink.controllers;


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
    public ResponseEntity<Tarefa> inserirTarefa(@RequestBody Tarefa tarefa) {
        Tarefa salvarTarefa = tarefaService.salvarTarefa(tarefa);
        return ResponseEntity.ok(salvarTarefa);
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<String> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarById(id);
        return ResponseEntity.ok("Tarefa deletada com sucesso."); // Mensagem de sucesso
    }

    @PutMapping(value = "/tarefas")
    public ResponseEntity<Tarefa> alterarTarefa(@RequestBody Tarefa tarefa){
        Tarefa tarefaNova = tarefaService.alterarTarefa(tarefa);
        return ResponseEntity.ok(tarefaNova);
    }

}
