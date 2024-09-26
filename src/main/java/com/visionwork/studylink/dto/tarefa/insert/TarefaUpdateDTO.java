package com.visionwork.studylink.dto.tarefa.insert;

import com.visionwork.studylink.entities.PrioridadeType;
import com.visionwork.studylink.entities.StatusType;

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
