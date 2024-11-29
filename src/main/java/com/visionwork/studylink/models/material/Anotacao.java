package com.visionwork.studylink.models.material;


import com.visionwork.studylink.dto.material.AnotacaoCreateDTO;
import jakarta.persistence.*;

@Table
@Entity
public class Anotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String conteudo;
    @ManyToOne
    private Material material;

    private Anotacao(Long id, String titulo, String conteudo, Material material) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.material = material;
    }

    public Anotacao() {
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

    public Material getMaterial() {
        return material;
    }

    public static final class Builder{
        private Long id;
        private String titulo;
        private String conteudo;
        private Material material;

        public Builder(){

        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder conteudo(String conteudo) {
            this.conteudo = conteudo;
            return this;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Anotacao build(){
            return new Anotacao(null, titulo, conteudo, material);
        }
    }
}
