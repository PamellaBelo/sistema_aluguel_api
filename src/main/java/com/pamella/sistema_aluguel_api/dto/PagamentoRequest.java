package com.pamella.sistema_aluguel_api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PagamentoRequest(
        @NotNull Long contratoId,
        @NotNull LocalDate dataPagamento,
        @NotNull LocalDate mesReferencia
) {}
