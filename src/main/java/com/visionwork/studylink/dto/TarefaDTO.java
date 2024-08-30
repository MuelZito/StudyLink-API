package com.visionwork.studylink.dto;

import com.visionwork.studylink.entities.Tarefa;

import java.time.LocalDate;

public class TarefaDTO {
    private String titulo;
    private LocalDate data_fim;
    private boolean concluida;
    private int prioridade;

    public TarefaDTO() {
    }

    public TarefaDTO(String titulo, LocalDate data_fim, boolean concluida, int prioridade) {
        this.titulo = titulo;
        this.data_fim = data_fim;
        this.concluida = concluida;
        this.prioridade = prioridade;
    }

    public TarefaDTO(Tarefa tarefa) {
        titulo = tarefa.getTitulo();
        data_fim = tarefa.getData_fim();
        concluida = tarefa.isConcluida();
        prioridade = tarefa.getPrioridade();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
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
