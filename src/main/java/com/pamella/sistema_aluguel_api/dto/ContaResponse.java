package com.pamella.sistema_aluguel_api.dto;

import com.pamella.sistema_aluguel_api.model.TipoConta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaResponse(
        Long id,
        Long contratoId,
        TipoConta tipo,
        BigDecimal valor,
        LocalDate vencimento
) {}
