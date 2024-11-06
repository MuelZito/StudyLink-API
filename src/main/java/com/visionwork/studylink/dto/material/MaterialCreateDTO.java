package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Visibilidade;

public record MaterialCreateDTO(
        String titulo,
        String areaConhecimento,
        String imagemBanner,
        Visibilidade visibilidade
) {
}
