package com.visionwork.studylink.dto.tarefa.read;

import com.visionwork.studylink.entities.Tarefa;

import java.time.LocalDate;

public class TarefaReadDTO {
    private Long id;
    private String titulo;
    private LocalDate dataFim;
    private boolean concluida;
    private int prioridade;

    public TarefaReadDTO() {
    }

    public TarefaReadDTO(Long id, String titulo, LocalDate dataFim, boolean concluida, int prioridade) {
        this.id = id;
        this.titulo = titulo;
        this.dataFim = dataFim;
        this.concluida = concluida;
        this.prioridade = prioridade;
    }

    public TarefaReadDTO(Tarefa tarefa) {
        id = tarefa.getId();
        titulo = tarefa.getTitulo();
        dataFim = tarefa.getDataFim();
        concluida = tarefa.isConcluida();
        prioridade = tarefa.getPrioridade();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}
