package com.visionwork.studylink.dto.tarefa.insert;

import java.time.LocalDateTime;

public record TarefaUpdateDTO(
        Long id,
        String titulo,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        String recurrenceRule,
        Long recurrenceID,
        String recurrenceException
) {
}