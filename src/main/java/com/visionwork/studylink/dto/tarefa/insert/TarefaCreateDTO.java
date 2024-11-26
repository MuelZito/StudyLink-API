package com.visionwork.studylink.dto.tarefa.insert;

import com.visionwork.studylink.models.tarefa.PrioridadeType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TarefaCreateDTO(
        String titulo,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        PrioridadeType prioridade,
        String color
) {
}
