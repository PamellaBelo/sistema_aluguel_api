package com.pamella.sistema_aluguel_api.dto;

import java.math.BigDecimal;

public record DashboardResponse(
        Long totalUnidades,
        Long casasAlugadas,
        Long casasVagas,
        BigDecimal valorTotalReceber,
        Long contasPendentes
) {}