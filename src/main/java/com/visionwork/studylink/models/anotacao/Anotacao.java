package com.visionwork.studylink.models.anotacao;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Anotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Lob
    @Column(columnDefinition = "JSON")
    private String conteudo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "anotacao_id")
    private List<Arquivo> arquivos = new ArrayList<>();

    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    @Entity
    public static class Arquivo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nomeOriginal;
        private String caminhoArquivo;
        private String tipoConteudo;
        private Long tamanho;

        public Arquivo() {}

        public Arquivo(String nomeOriginal, String caminhoArquivo, String tipoConteudo, Long tamanho) {
            this.nomeOriginal = nomeOriginal;
            this.caminhoArquivo = caminhoArquivo;
            this.tipoConteudo = tipoConteudo;
            this.tamanho = tamanho;
        }

        // Getters e Setters completos...
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNomeOriginal() { return nomeOriginal; }
        public void setNomeOriginal(String nomeOriginal) { this.nomeOriginal = nomeOriginal; }
        public String getCaminhoArquivo() { return caminhoArquivo; }
        public void setCaminhoArquivo(String caminhoArquivo) { this.caminhoArquivo = caminhoArquivo; }
        public String getTipoConteudo() { return tipoConteudo; }
        public void setTipoConteudo(String tipoConteudo) { this.tipoConteudo = tipoConteudo; }
        public Long getTamanho() { return tamanho; }
        public void setTamanho(Long tamanho) { this.tamanho = tamanho; }
    }

    public void adicionarArquivo(Arquivo arquivo) {
        this.arquivos.add(arquivo);
    }

    public void removerArquivo(Arquivo arquivo) {
        this.arquivos.remove(arquivo);
    }

    // Getters e Setters completos...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public List<Arquivo> getArquivos() { return arquivos; }
    public void setArquivos(List<Arquivo> arquivos) { this.arquivos = arquivos; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}