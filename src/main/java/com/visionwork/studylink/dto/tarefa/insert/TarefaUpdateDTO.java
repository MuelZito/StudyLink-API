package com.visionwork.studylink.dto.tarefa.insert;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TarefaUpdateDTO(
        Long id,
        @NotBlank(message = "O título não pode ser vazio")
        @Size(max = 100)
        String titulo,
        @Size(max = 100)
        String descricao,
        @FutureOrPresent(message = "A data de inicio deve estar no presente ou futuro")
        LocalDateTime dataInicio,
        @FutureOrPresent(message = "A data de inicio deve estar no presente ou futuro")
        LocalDateTime dataFim,
        String recurrenceRule,
        Long recurrenceID,
        String recurrenceException
) {
}