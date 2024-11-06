package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Visibilidade;

public record MaterialUpdateDTO(
        String titulo,
        String areaConhecimento,
        String imagemBanner,
        Visibilidade visibilidade
) {
}
