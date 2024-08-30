package com.visionwork.studylink.dto;

import com.visionwork.studylink.entities.Tarefa;

import java.time.LocalDate;

public class TarefaDTO {
    private String titulo;
    private LocalDate dataFim;
    private boolean concluida;
    private int prioridade;

    public TarefaDTO() {
    }

    public TarefaDTO(String titulo, LocalDate dataFim, boolean concluida, int prioridade) {
        this.titulo = titulo;
        this.dataFim = dataFim;
        this.concluida = concluida;
        this.prioridade = prioridade;
    }

    public TarefaDTO(Tarefa tarefa) {
        titulo = tarefa.getTitulo();
        dataFim = tarefa.getDataFim();
        concluida = tarefa.isConcluida();
        prioridade = tarefa.getPrioridade();
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
