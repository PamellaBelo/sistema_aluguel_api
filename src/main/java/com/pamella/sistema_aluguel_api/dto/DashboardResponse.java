package com.pamella.sistema_aluguel_api.dto;

import java.math.BigDecimal;

public record DashboardResponse(
        long totalImoveis,
        long totalUnidades,
        long casasAlugadas,
        long casasVagas,
        BigDecimal valorTotalReceber,
        long contasPendentes
) {}