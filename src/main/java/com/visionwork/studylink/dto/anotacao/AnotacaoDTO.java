package com.visionwork.studylink.dto.anotacao;

import com.visionwork.studylink.models.anotacao.Anotacao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AnotacaoDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private List<ArquivoDTO> arquivos;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Getters and Setters for AnotacaoDTO
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public List<ArquivoDTO> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<ArquivoDTO> arquivos) {
        this.arquivos = arquivos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public static class ArquivoDTO {
        private Long id;
        private String nomeOriginal;
        private String caminhoArquivo;
        private String tipoConteudo;
        private Long tamanho;

        // Getters and Setters for ArquivoDTO
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNomeOriginal() {
            return nomeOriginal;
        }

        public void setNomeOriginal(String nomeOriginal) {
            this.nomeOriginal = nomeOriginal;
        }

        public String getCaminhoArquivo() {
            return caminhoArquivo;
        }

        public void setCaminhoArquivo(String caminhoArquivo) {
            this.caminhoArquivo = caminhoArquivo;
        }

        public String getTipoConteudo() {
            return tipoConteudo;
        }

        public void setTipoConteudo(String tipoConteudo) {
            this.tipoConteudo = tipoConteudo;
        }

        public Long getTamanho() {
            return tamanho;
        }

        public void setTamanho(Long tamanho) {
            this.tamanho = tamanho;
        }
    }

    public static AnotacaoDTO fromEntity(Anotacao anotacao) {
        AnotacaoDTO dto = new AnotacaoDTO();
        dto.setId(anotacao.getId());
        dto.setTitulo(anotacao.getTitulo());
        dto.setConteudo(anotacao.getConteudo());
        dto.setDataCriacao(anotacao.getDataCriacao());
        dto.setDataAtualizacao(anotacao.getDataAtualizacao());

        dto.setArquivos(anotacao.getArquivos().stream()
                .map(arquivo -> {
                    ArquivoDTO arquivoDTO = new ArquivoDTO();
                    arquivoDTO.setId(arquivo.getId());
                    arquivoDTO.setNomeOriginal(arquivo.getNomeOriginal());
                    arquivoDTO.setCaminhoArquivo(arquivo.getCaminhoArquivo());
                    arquivoDTO.setTipoConteudo(arquivo.getTipoConteudo());
                    arquivoDTO.setTamanho(arquivo.getTamanho());
                    return arquivoDTO;
                })
                .collect(Collectors.toList())
        );

        return dto;
    }
}