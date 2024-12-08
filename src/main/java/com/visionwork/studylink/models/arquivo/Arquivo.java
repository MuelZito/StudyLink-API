package com.visionwork.studylink.models.arquivo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visionwork.studylink.models.anotacao.Anotacao;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Entity
@Table(name = "Arquivos")
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "anotacao_id", nullable = false)
    @JsonBackReference
    private Anotacao anotacao;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        if (StringUtils.isNotBlank(conteudo) && anotacao != null) {
            String primeiraLinha = extrairPrimeiraLinha(conteudo);
            anotacao.setTitulo(primeiraLinha);
            anotacao.setDataUltimaAlteracao(LocalDateTime.now());
        }
    }

    private String extrairPrimeiraLinha(String conteudoQuill) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(conteudoQuill);
            JsonNode opsNode = rootNode.get("ops");

            if (opsNode != null && opsNode.isArray()) {
                for (JsonNode op : opsNode) {
                    if (op.has("insert") && op.get("insert").isTextual()) {
                        String texto = op.get("insert").asText().trim();
                        if (!texto.isEmpty()) {
                            return texto.split("\n")[0];
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Log do erro
        }
        return "Nova Anotação";
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Anotacao getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(Anotacao anotacao) {
        this.anotacao = anotacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}