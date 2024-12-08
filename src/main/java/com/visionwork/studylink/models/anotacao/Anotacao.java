package com.visionwork.studylink.models.anotacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.visionwork.studylink.models.arquivo.Arquivo;
import com.visionwork.studylink.models.material.Material;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "Anotacao")
public class Anotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataUltimaAlteracao;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "arquivo_id", unique = true)
    @JsonManagedReference
    private Arquivo arquivo;


    @PrePersist
    protected void onCreate() {
        dataUltimaAlteracao = LocalDateTime.now();
        titulo = StringUtils.isBlank(titulo) ? "Nova Anotação" : titulo;
    }

    // Métodos de manipulação do arquivo
    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
        if (arquivo != null) {
            arquivo.setAnotacao(this);
        }
    }

    public void removerArquivo() {
        if (this.arquivo != null) {
            this.arquivo.setAnotacao(null);
            this.arquivo = null;
        }
    }

    // Construtor vazio
    private Anotacao() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public static class Builder {
        private Long id;
        private Material material;
        private String titulo;
        private LocalDateTime dataUltimaAlteracao;
        private Arquivo arquivo;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder dataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
            this.dataUltimaAlteracao = dataUltimaAlteracao;
            return this;
        }

        public Builder arquivo(Arquivo arquivo) {
            this.arquivo = arquivo;
            return this;
        }

        public Anotacao build() {
            Anotacao anotacao = new Anotacao();
            anotacao.setId(this.id);
            anotacao.setMaterial(this.material);
            anotacao.setTitulo(this.titulo);
            anotacao.setDataUltimaAlteracao(this.dataUltimaAlteracao);
            anotacao.setArquivo(this.arquivo);
            return anotacao;
        }
    }
}
