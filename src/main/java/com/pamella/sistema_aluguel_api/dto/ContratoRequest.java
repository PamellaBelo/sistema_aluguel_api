package com.pamella.sistema_aluguel_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratoRequest(
        @NotNull Long unidadeId,
        @NotNull Long inquilinoId,
        @Positive BigDecimal valorAluguel,
        @NotNull LocalDate dataInicio,
        @NotNull LocalDate dataFim
) {}
