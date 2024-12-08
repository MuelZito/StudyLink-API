package com.visionwork.studylink.dto.anotacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visionwork.studylink.models.anotacao.Anotacao;

import java.time.LocalDateTime;

public record AnotacaoReadDTO(
        Long id,
        String titulo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataUltimaAlteracao,
        Long materialId
) {
    public AnotacaoReadDTO(Anotacao anotacao) {
        this(
                anotacao.getId(),
                anotacao.getTitulo(),
                anotacao.getDataUltimaAlteracao(),
                anotacao.getMaterial().getId()
        );
    }
}
