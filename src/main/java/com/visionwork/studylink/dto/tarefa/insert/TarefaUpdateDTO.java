package com.visionwork.studylink.dto.tarefa.insert;

import com.visionwork.studylink.models.tarefa.PrioridadeType;
import com.visionwork.studylink.models.tarefa.StatusType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TarefaUpdateDTO(
        String titulo,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        PrioridadeType prioridade,
        String color

) {
}
