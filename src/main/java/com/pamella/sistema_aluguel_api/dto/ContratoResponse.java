package com.pamella.sistema_aluguel_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoResponse(
        Long id,
        Long casaId,
        String numeroCasa,
        Long inquilinoId,
        String nomeInquilino,
        BigDecimal valorAluguel,
        LocalDate dataInicio,
        LocalDate dataFim,
        boolean ativo
) {}
