package com.visionwork.studylink.models.tarefa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.visionwork.studylink.dto.tarefa.insert.TarefaUpdateDTO;
import com.visionwork.studylink.models.usuario.Usuario;
import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    @Enumerated(EnumType.STRING)
    private PrioridadeType prioridade;
    private String color;
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    public Tarefa() {
    }

    public Tarefa(Long id, String titulo, String descricao, LocalDate dataInicio, LocalDate dataFim,
                   PrioridadeType prioridade,  String color, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.prioridade = prioridade;
        this.color = color;
        this.usuario = usuario;
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }


    public PrioridadeType getPrioridade() {
        return prioridade;
    }

    public String getColor() {return  color;}

    public Usuario getUsuario() {
        return usuario;
    }

    public void update(TarefaUpdateDTO tarefaAtualizada) {
        this.titulo = tarefaAtualizada.titulo();
        this.descricao = tarefaAtualizada.descricao();
        this.dataInicio = tarefaAtualizada.dataInicio();
        this.dataFim = tarefaAtualizada.dataFim();
        this.prioridade = tarefaAtualizada.prioridade();
        this.color = tarefaAtualizada.color();
    }

    public static final class Builder {
        private String titulo;
        private String descricao;
        private LocalDate dataInicio;
        private LocalDate dataFim;
        private PrioridadeType prioridade;
        private String color;
        private Usuario usuario;

        public Builder() {
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder dataInicio(LocalDate dataInicio) {
            this.dataInicio = dataInicio;
            return this;
        }

        public Builder dataFim(LocalDate dataFim) {
            this.dataFim = dataFim;
            return this;
        }

        public Builder prioridade(PrioridadeType prioridade) {
            this.prioridade = prioridade;
            return this;
        }
        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Tarefa build() {
            return new Tarefa(null, titulo, descricao, dataInicio, dataFim, prioridade,color, usuario);
        }
    }
}
