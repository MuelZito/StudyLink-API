package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.material.Visibilidade;

import java.util.List;

public class MaterialReadDTO {
    private final Long id;
    private String titulo;
    private String areaConhecimento;
    private String imagemBanner;
    private Visibilidade visibilidade;

    public MaterialReadDTO(Long id, String titulo, String areaConhecimento, String imagemBanner, Visibilidade visibilidade) {
        this.id = id;
        this.titulo = titulo;
        this.areaConhecimento = areaConhecimento;
        this.imagemBanner = imagemBanner;
        this.visibilidade = visibilidade;
    }

    public MaterialReadDTO(Material material) {
        this.id = material.getId();
        this.titulo = material.getTitulo();
        this.areaConhecimento = material.getAreaConhecimento();
        this.imagemBanner = material.getImagemBanner();
        this.visibilidade = material.getVisibilidade();
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


}
