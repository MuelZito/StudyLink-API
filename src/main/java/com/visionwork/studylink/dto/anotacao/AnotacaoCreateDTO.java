package com.visionwork.studylink.dto.anotacao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AnotacaoCreateDTO(
        String titulo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataUltimaAlteracao,
        Long materialId
) {
}
