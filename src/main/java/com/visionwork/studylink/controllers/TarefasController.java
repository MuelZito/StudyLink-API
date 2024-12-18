package com.visionwork.studylink.controllers;


import com.visionwork.studylink.dto.tarefa.insert.TarefaCreateDTO;
import com.visionwork.studylink.dto.tarefa.insert.TarefaUpdateDTO;
import com.visionwork.studylink.dto.tarefa.read.TarefaReadDTO;
import com.visionwork.studylink.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TarefasController {

    @Autowired
    TarefaService tarefaService;

    @PostMapping(value = "/tarefas")
    public ResponseEntity<TarefaReadDTO> inserirTarefa(@RequestBody @Valid TarefaCreateDTO tarefa) {
        TarefaReadDTO criarTarefa = tarefaService.criarTarefa(tarefa);
        return ResponseEntity.ok(criarTarefa);
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/tarefas/{id}")
    public ResponseEntity<TarefaReadDTO> alterarTarefa(
            @PathVariable Long id,
            @RequestBody @Valid TarefaUpdateDTO tarefa,
            @RequestParam(required = false, defaultValue = "false") boolean editarRecorrenciaInteira) {

        TarefaReadDTO tarefaAtualizada;

        if (tarefa.recurrenceID() != null && !editarRecorrenciaInteira) {
            tarefaAtualizada = tarefaService.alterarOcorrenciaUnica(id, tarefa);
        } else {
            tarefaAtualizada = tarefaService.alterarTarefa(id, tarefa, editarRecorrenciaInteira);
        }

        return ResponseEntity.ok(tarefaAtualizada);
    }
    @GetMapping(value = "/tarefas/{dataInicio}/{dataFim}")
    public ResponseEntity<List<TarefaReadDTO>> buscarTarefas(
            @PathVariable("dataInicio") LocalDateTime dataInicio,
            @PathVariable("dataFim") LocalDateTime dataFim
    ){
        List<TarefaReadDTO> tarefas = tarefaService.buscarTarefas(dataInicio, dataFim);
        return ResponseEntity.ok(tarefas);
    }

}
