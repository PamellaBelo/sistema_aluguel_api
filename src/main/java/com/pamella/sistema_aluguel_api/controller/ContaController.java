package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.ContaRequest;
import com.pamella.sistema_aluguel_api.dto.ContaResponse;
import com.pamella.sistema_aluguel_api.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService service;

    @PostMapping
    public ResponseEntity<ContaResponse> criar(@Valid @RequestBody ContaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<List<ContaResponse>> listarPorContrato(@PathVariable Long contratoId) {
        return ResponseEntity.ok(service.listarPorContrato(contratoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
