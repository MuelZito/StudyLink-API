package com.visionwork.studylink.dto.tarefa.read;

import com.visionwork.studylink.models.tarefa.PrioridadeType;
import com.visionwork.studylink.models.tarefa.StatusType;
import com.visionwork.studylink.models.tarefa.Tarefa;

import java.time.LocalDate;

public class TarefaReadDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataFim;
    private PrioridadeType prioridade;
    private String color;

    public TarefaReadDTO() {
    }

    public TarefaReadDTO(Long id, String titulo, String descricao, LocalDate dataFim, PrioridadeType prioridade, String color) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataFim = dataFim;
        this.prioridade = prioridade;
        this.color = color;
    }

    public TarefaReadDTO(Tarefa tarefa) {
        id = tarefa.getId();
        titulo = tarefa.getTitulo();
        descricao = tarefa.getDescricao();
        dataFim = tarefa.getDataFim();
        prioridade = tarefa.getPrioridade();
        color = tarefa.getColor();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
    public LocalDate getDataFim() {
        return dataFim;
    }


    public PrioridadeType getPrioridade() {
        return prioridade;
    }

    public String getColor() {
        return color;
    }

}
