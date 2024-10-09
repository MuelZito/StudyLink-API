package com.visionwork.studylink.dto.tarefa.insert;

import com.visionwork.studylink.models.tarefa.PrioridadeType;
import com.visionwork.studylink.models.tarefa.StatusType;

import java.time.LocalDate;

public record TarefaUpdateDTO(
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        PrioridadeType prioridade,
        StatusType status
) {
}
