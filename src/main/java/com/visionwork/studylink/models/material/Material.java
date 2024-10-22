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
    @ManyToOne
    @JsonBackReference
    private Usuario usuario;
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<Anotacao> anotacoes;

    public Material() {
    }

    private Material(Long id, String titulo, String areaConhecimento, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.areaConhecimento = areaConhecimento;
        this.usuario = usuario;
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

    public String getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(String areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public void update(MaterialUpdateDTO materialAtualizado){
        this.titulo = materialAtualizado.titulo();
        this.areaConhecimento = materialAtualizado.areaConhecimento();
    }

    public static final class Builder {
        private Long id;
        private String titulo;
        private String areaConhecimento;
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

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder anotacoes(List<Anotacao> anotacoes) {
            this.anotacoes = anotacoes;
            return this;
        }

        public Material build() {
            Material material = new Material();
            material.setId(id);
            material.setTitulo(titulo);
            material.setAreaConhecimento(areaConhecimento);
            material.setUsuario(usuario);
            material.setAnotacoes(anotacoes);
            return material;
        }
    }
}
