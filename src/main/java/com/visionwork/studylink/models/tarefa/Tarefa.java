package com.visionwork.studylink.models.tarefa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.visionwork.studylink.dto.tarefa.insert.TarefaUpdateDTO;
import com.visionwork.studylink.models.usuario.Usuario;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataInicio;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataFim;
    private String recurrenceRule;
    private Long recurrenceID;
    private String recurrenceException;
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;

    public Tarefa() {
    }


    public Tarefa(Long id, String titulo, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim,
                  String recurrenceRule, Long recurrenceID, String recurrenceException, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.recurrenceRule = recurrenceRule;
        this.recurrenceID = recurrenceID;
        this.recurrenceException = recurrenceException;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
     public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
         this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
         this.dataFim = dataFim;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public Long getRecurrenceID() {
        return recurrenceID;
    }

    public void setRecurrenceID(Long recurrenceID) {
        this.recurrenceID = recurrenceID;
    }

    public String getRecurrenceException() {
        return recurrenceException;
    }

    public void setRecurrenceException(String recurrenceException) {
        this.recurrenceException = recurrenceException;
    }


    public Usuario getUsuario() {
        return usuario;
    }




    public void update(TarefaUpdateDTO tarefaAtualizada) {
        this.id = tarefaAtualizada.id();
        this.titulo = tarefaAtualizada.titulo();
        this.descricao = tarefaAtualizada.descricao();
        this.dataInicio = tarefaAtualizada.dataInicio();
        this.dataFim = tarefaAtualizada.dataFim();
        this.recurrenceRule = tarefaAtualizada.recurrenceRule();
        this.recurrenceID = tarefaAtualizada.recurrenceID();
        this.recurrenceException = tarefaAtualizada.recurrenceException();
    }

    public static final class Builder {
        private String titulo;
        private String descricao;
        private LocalDateTime dataInicio;
        private LocalDateTime dataFim;
        private String recurrenceRule;
        private Long recurrenceID;
        private String recurrenceException;
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

        public Builder dataInicio(LocalDateTime dataInicio) {
            this.dataInicio = dataInicio;
            return this;
        }

        public Builder dataFim(LocalDateTime dataFim) {
            this.dataFim = dataFim;
            return this;
        }

        public Builder recurrenceRule(String recurrenceRule) {
            this.recurrenceRule = recurrenceRule;
            return this;
        }

        public Builder recurrenceID(Long recurrenceID) {
            this.recurrenceID = recurrenceID;
            return this;
        }

        public Builder recurrenceException(String recurrenceException) {
            this.recurrenceException = recurrenceException;
            return this;
        }

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Tarefa build() {
            return new Tarefa(null, titulo, descricao, dataInicio, dataFim,
                    recurrenceRule, recurrenceID, recurrenceException, usuario);
        }
    }
}