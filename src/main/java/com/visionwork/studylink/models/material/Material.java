package com.visionwork.studylink.models.material;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String banner;


    @Enumerated(EnumType.STRING)
    private Visibilidade visibilidade;
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<Anotacao> anotacoes;

    public Material() {
    }

    private Material(Long id, String titulo, String areaConhecimento, String banner, Visibilidade visibilidade, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.areaConhecimento = areaConhecimento;
        this.banner = banner;
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

    public String getBanner() {
        return banner;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void update(MaterialUpdateDTO materialAtualizado) {
        this.titulo = materialAtualizado.titulo();
        this.areaConhecimento = materialAtualizado.areaConhecimento();
        this.banner = materialAtualizado.banner();
        this.visibilidade = materialAtualizado.visibilidade();
    }

    public static final class Builder {
        private Long id;
        private String titulo;
        private String areaConhecimento;
        private String banner;
        private Visibilidade visibilidade = Visibilidade.PRIVADO;

        private Usuario usuario;
        private List<Anotacao> anotacoes;

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

        public Builder banner(String banner) {
            this.banner = banner;
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

        public Builder anotacoes(List<Anotacao> anotacoes) {
            this.anotacoes = anotacoes;
            return this;
        }

        public Material build() {
            return new Material(null, titulo, areaConhecimento, banner, visibilidade, usuario);
        }
    }
}
