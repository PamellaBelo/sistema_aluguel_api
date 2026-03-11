package com.pamella.sistema_aluguel_api.dto;


import com.pamella.sistema_aluguel_api.model.StatusCasa;

public record CasaResponse(Long id, String numero, Long unidadeId, String nomeUnidade, StatusCasa status) {}