package com.visionwork.studylink.models.material;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    public Material(Long id, String titulo, String areaConhecimento, Usuario usuario) {
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
}
