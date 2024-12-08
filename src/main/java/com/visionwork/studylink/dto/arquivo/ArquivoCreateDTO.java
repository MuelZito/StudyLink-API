package com.visionwork.studylink.dto.arquivo;

public class ArquivoCreateDTO {

    private String conteudo;
    private Long anotacaoId;

    // Construtor
    public ArquivoCreateDTO(Long anotacaoId ,String conteudo)
    {
        this.anotacaoId = anotacaoId;
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setAnotacaoId(Long anotacaoId) {

        this.anotacaoId = anotacaoId;
    }


    public Long getAnotacaoId() {
        return anotacaoId;
    }


}