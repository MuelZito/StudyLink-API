package com.visionwork.studylink.dto.tarefa.insert;

import java.time.LocalDateTime;

public record TarefaCreateDTO(
        String titulo,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String recurrenceRule,
        Long recurrenceID,
        String recurrenceException
) {
}