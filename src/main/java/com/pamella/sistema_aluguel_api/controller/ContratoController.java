package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.ContratoRequest;
import com.pamella.sistema_aluguel_api.dto.ContratoResponse;
import com.pamella.sistema_aluguel_api.service.ContratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final ContratoService service;

    @PostMapping
    public ResponseEntity<ContratoResponse> criar(@Valid @RequestBody ContratoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ContratoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ✅ Encerrar contrato (PUT, não DELETE — o contrato fica no histórico)
    @PutMapping("/{id}/encerrar")
    public ResponseEntity<ContratoResponse> encerrar(@PathVariable Long id) {
        return ResponseEntity.ok(service.encerrar(id));
    }

    @GetMapping("/{id}/total-mensal")
    public ResponseEntity<BigDecimal> totalMensal(@PathVariable Long id) {
        return ResponseEntity.ok(service.calcularTotalMensal(id));
    }
}
