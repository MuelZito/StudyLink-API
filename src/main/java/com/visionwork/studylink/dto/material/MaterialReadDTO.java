package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Anotacao;
import com.visionwork.studylink.models.material.Material;

import java.util.List;

public class MaterialReadDTO {
    private final Long id;
    private String titulo;
    private String areaConhecimento;
    private List<Anotacao> anotacoes;

    public MaterialReadDTO(Long id, String titulo, String areaConhecimento, List<Anotacao> anotacoes) {
        this.id = id;
        this.titulo = titulo;
        this.areaConhecimento = areaConhecimento;
        this.anotacoes = anotacoes;
    }

    public MaterialReadDTO(Material material) {
        this.id = material.getId();
        this.titulo = material.getTitulo();
        this.areaConhecimento = material.getAreaConhecimento();
        this.anotacoes = material.getAnotacoes();
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

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }
}
