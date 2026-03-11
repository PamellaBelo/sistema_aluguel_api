package com.pamella.sistema_aluguel_api.controller;


import com.pamella.sistema_aluguel_api.dto.PagamentoRequest;
import com.pamella.sistema_aluguel_api.dto.PagamentoResponse;
import com.pamella.sistema_aluguel_api.model.Pagamento;
import com.pamella.sistema_aluguel_api.service.PagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService service;

    @PostMapping
    public ResponseEntity<PagamentoResponse> registrar(@Valid @RequestBody PagamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<PagamentoResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<List<PagamentoResponse>> listarPorContrato(@PathVariable Long contratoId) {
        return ResponseEntity.ok(service.listarPorContrato(contratoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
