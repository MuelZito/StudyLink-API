package com.visionwork.studylink.dto.tarefa.insert;

import com.visionwork.studylink.entities.PrioridadeType;

import java.time.LocalDate;

public record TarefaCreateDTO(
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        PrioridadeType prioridade
) {
}
