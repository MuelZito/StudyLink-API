package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Anotacao;
import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.material.Visibilidade;

import java.util.List;

public class MaterialReadDTO {
    private final Long id;
    private String titulo;
    private String areaConhecimento;
    private String banner;
    private Visibilidade visibilidade;
    private List<Anotacao> anotacoes;

    public MaterialReadDTO(Long id, String titulo, String areaConhecimento, String banner, Visibilidade visibilidade, List<Anotacao> anotacoes) {
        this.id = id;
        this.titulo = titulo;
        this.areaConhecimento = areaConhecimento;
        this.banner = banner;
        this.visibilidade = visibilidade;
        this.anotacoes = anotacoes;
    }

    public MaterialReadDTO(Material material) {
        this.id = material.getId();
        this.titulo = material.getTitulo();
        this.areaConhecimento = material.getAreaConhecimento();
        this.banner = material.getBanner();
        this.visibilidade = material.getVisibilidade();
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

    public String getBanner() {
        return banner;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }
}
