package com.visionwork.studylink.dto.arquivo;

import com.visionwork.studylink.models.anotacao.Anotacao;

import java.util.logging.LogManager;

public class ArquivoReadDTO {

    private Long id;
    private String conteudo;
    private  Long anotacaoId;

    // Construtores
    public ArquivoReadDTO(Long id, String conteudo, Long anotacaoId) {
        this.id = id;
        this.conteudo = conteudo;
        this.anotacaoId = anotacaoId;

    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnotacaoId() {
        return anotacaoId;
    }

    public void setAnotacaoId(Long anotacaoId) {
        this.anotacaoId = anotacaoId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
