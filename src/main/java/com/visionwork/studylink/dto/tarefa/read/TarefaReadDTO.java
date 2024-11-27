package com.visionwork.studylink.dto.tarefa.read;

import com.visionwork.studylink.models.tarefa.Tarefa;

import java.time.LocalDateTime;

public class TarefaReadDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String recurrenceRule;
    private Long recurrenceID;
    private String recurrenceException;

    public TarefaReadDTO() {
    }

    public TarefaReadDTO(Long id, String titulo, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim,
                         String recurrenceRule, Long recurrenceID, String recurrenceException) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.recurrenceRule = recurrenceRule;
        this.recurrenceID = recurrenceID;
        this.recurrenceException = recurrenceException;
    }

    public TarefaReadDTO(Tarefa tarefa) {
        id = tarefa.getId();
        titulo = tarefa.getTitulo();
        descricao = tarefa.getDescricao();
        dataInicio = tarefa.getDataInicio();
        dataFim = tarefa.getDataFim();
        recurrenceRule = tarefa.getRecurrenceRule();
        recurrenceID = tarefa.getRecurrenceID();
        recurrenceException = tarefa.getRecurrenceException();
    }

    // Getters para os novos campos
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public Long getRecurrenceID() {
        return recurrenceID;
    }

    public String getRecurrenceException() {
        return recurrenceException;
    }
}