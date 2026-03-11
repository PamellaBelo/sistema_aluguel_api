package com.pamella.sistema_aluguel_api.controller;


import com.pamella.sistema_aluguel_api.dto.CasaRequest;
import com.pamella.sistema_aluguel_api.dto.CasaResponse;
import com.pamella.sistema_aluguel_api.model.StatusCasa;
import com.pamella.sistema_aluguel_api.service.CasaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casas")
@RequiredArgsConstructor
public class CasaController {

    private final CasaService casaService;

    @PostMapping
    public ResponseEntity<CasaResponse> criar(@Valid @RequestBody CasaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(casaService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<CasaResponse>> listarTodas() {
        return ResponseEntity.ok(casaService.listarTodas());
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<CasaResponse>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(casaService.listarPorUnidade(unidadeId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CasaResponse>> listarPorStatus(@PathVariable StatusCasa status) {
        return ResponseEntity.ok(casaService.listarPorStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        casaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

