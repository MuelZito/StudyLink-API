package com.visionwork.studylink.models.material;

import com.visionwork.studylink.models.material.Anotacao;
import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("Anotacao")
public class Texto extends Anotacao {
    private String conteudo;
}
