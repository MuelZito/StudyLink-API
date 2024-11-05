package com.visionwork.studylink.dto.material;

import com.visionwork.studylink.models.material.Material;
import com.visionwork.studylink.models.material.Visibilidade;

public record MaterialSearchDTO(
        String titulo,
        String areaConhecimento,

        Visibilidade visibilidade,
        String usuarioDono
) {
    public MaterialSearchDTO(Material material) {
        this(
                material.getTitulo(),
                material.getAreaConhecimento(),
                material.getVisibilidade(),
                material.getUsuario().getNomeUsuario());
    }
}
