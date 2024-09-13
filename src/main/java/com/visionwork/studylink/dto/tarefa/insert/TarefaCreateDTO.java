package com.visionwork.studylink.dto.tarefa.insert;

import java.time.LocalDate;

public record TarefaCreateDTO(
        String titulo,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        Integer prioridade
) {
}
