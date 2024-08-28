package com.visionwork.studylink.controllers;


import com.visionwork.studylink.entities.Tarefa;
import com.visionwork.studylink.repositories.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tarefa")
public class TarefasController {

    @Autowired
    TarefasRepository tarefasRepository;


    @GetMapping
    List<Tarefa> findAll(){
        List<Tarefa> tarefas = tarefasRepository.findAll();
        return  tarefas;
    }


}
