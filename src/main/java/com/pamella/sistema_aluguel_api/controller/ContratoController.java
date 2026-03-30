package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.ContratoRequest;
import com.pamella.sistema_aluguel_api.dto.ContratoResponse;
import com.pamella.sistema_aluguel_api.service.ContratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final ContratoService contratoService;

    @PostMapping
    public ResponseEntity<ContratoResponse> criar(@RequestBody @Valid ContratoRequest request) {
        return ResponseEntity.ok(contratoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ContratoResponse>> listar() {
        return ResponseEntity.ok(contratoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<ContratoResponse> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.encerrar(id));
    }

    @GetMapping("/{id}/total-mensal")
    public ResponseEntity<BigDecimal> totalMensal(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.calcularTotalMensal(id));
    }
}