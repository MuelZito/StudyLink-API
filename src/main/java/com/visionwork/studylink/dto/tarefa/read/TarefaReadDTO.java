package com.visionwork.studylink.dto.tarefa.read;

import com.visionwork.studylink.entities.PrioridadeType;
import com.visionwork.studylink.entities.StatusType;
import com.visionwork.studylink.entities.Tarefa;

import java.time.LocalDate;

public class TarefaReadDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataFim;
    private StatusType status;
    private PrioridadeType prioridade;

    public TarefaReadDTO() {
    }

    public TarefaReadDTO(Long id, String titulo, String descricao, LocalDate dataFim, StatusType status, PrioridadeType prioridade) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataFim = dataFim;
        this.status = status;
        this.prioridade = prioridade;
    }

    public TarefaReadDTO(Tarefa tarefa) {
        id = tarefa.getId();
        titulo = tarefa.getTitulo();
        descricao = tarefa.getDescricao();
        dataFim = tarefa.getDataFim();
        status = tarefa.getStatus();
        prioridade = tarefa.getPrioridade();
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

    public StatusType getStatus() {
        return status;
    }


    public PrioridadeType getPrioridade() {
        return prioridade;
    }

}
