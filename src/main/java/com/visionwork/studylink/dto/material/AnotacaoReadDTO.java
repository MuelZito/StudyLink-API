package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Anotacao;

public class AnotacaoReadDTO {
    private Long id;
    private String titulo;
    private String conteudo;

    public AnotacaoReadDTO(Long id, String titulo, String conteudo) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public AnotacaoReadDTO(Anotacao anotacao){
        this.id = anotacao.getId();
        this.titulo = anotacao.getTitulo();
        this.conteudo = anotacao.getConteudo();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }
}

