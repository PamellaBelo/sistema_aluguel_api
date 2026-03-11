package com.pamella.sistema_aluguel_api.dto;

import com.pamella.sistema_aluguel_api.model.TipoConta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaRequest(
        @NotNull Long contratoId,
        @NotNull TipoConta tipo,
        @NotNull @Positive BigDecimal valor,
        @NotNull LocalDate vencimento
) {}