package com.pamella.sistema_aluguel_api.dto;

import com.pamella.sistema_aluguel_api.model.StatusCasa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CasaRequest(
        @NotNull Long unidadeId,
        @NotBlank String numero,
        StatusCasa status
) {}