package com.pamella.sistema_aluguel_api.dto;

import com.pamella.sistema_aluguel_api.model.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoResponse(
        Long id,
        Long contratoId,
        String nomeInquilino,
        String nomeUnidade,
        String nomeImovel,
        BigDecimal valorPago,
        LocalDate dataPagamento,
        LocalDate mesReferencia,
        StatusPagamento status
) {}