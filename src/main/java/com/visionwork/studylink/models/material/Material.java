package com.visionwork.studylink.models.material;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.visionwork.studylink.dto.material.MaterialUpdateDTO;
import com.visionwork.studylink.models.usuario.Usuario;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String areaConhecimento;
    @Column(columnDefinition = "LONGTEXT")
    private String imagemBanner;
    @Enumerated(EnumType.STRING)
    private Visibilidade visibilidade;
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;
    @OneToOne(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private Anotacao anotacao;

    public Material() {
    }

    private Material(Long id, String titulo, String areaConhecimento, String imagemBanner, Visibilidade visibilidade, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.areaConhecimento = areaConhecimento;
        this.imagemBanner = imagemBanner;
        this.visibilidade = visibilidade;
        this.usuario = usuario;
    }


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAreaConhecimento() {
        return areaConhecimento;
    }

    public String getImagemBanner() {
        return imagemBanner;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Anotacao getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(Anotacao anotacao) {
        this.anotacao = anotacao;
    }

    public void update(MaterialUpdateDTO materialAtualizado) {
        this.titulo = materialAtualizado.titulo();
        this.areaConhecimento = materialAtualizado.areaConhecimento();
        this.imagemBanner = materialAtualizado.imagemBanner();
        this.visibilidade = materialAtualizado.visibilidade();
    }

    public static final class Builder {
        private Long id;
        private String titulo;
        private String areaConhecimento;
        private String imagemBanner;
        private Visibilidade visibilidade = Visibilidade.PRIVADO;

        private Usuario usuario;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder areaConhecimento(String areaConhecimento) {
            this.areaConhecimento = areaConhecimento;
            return this;
        }

        public Builder imagemBanner(String imageBanner) {
            this.imagemBanner = imageBanner;
            return this;
        }

        public Builder visibilidade(Visibilidade visibilidade) {
            this.visibilidade = visibilidade;
            return this;
        }

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Material build() {
            return new Material(null, titulo, areaConhecimento, imagemBanner, visibilidade, usuario);
        }
    }
}
