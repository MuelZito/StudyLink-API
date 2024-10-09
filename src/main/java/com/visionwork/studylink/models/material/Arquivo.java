package com.visionwork.studylink.models.material;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("Arquivo")
@Entity
public class Arquivo extends Anotacao {
    private String arquivoBase64;
}
