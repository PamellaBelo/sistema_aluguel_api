package com.pamella.sistema_aluguel_api.dto;

import com.pamella.sistema_aluguel_api.model.StatusUnidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnidadeRequest(
        @NotBlank String nome,
        @NotNull Long imovelId,
        StatusUnidade status) {}
