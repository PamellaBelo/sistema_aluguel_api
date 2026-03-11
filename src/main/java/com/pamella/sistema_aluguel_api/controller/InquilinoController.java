package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.InquilinoRequest;
import com.pamella.sistema_aluguel_api.dto.InquilinoResponse;
import com.pamella.sistema_aluguel_api.service.InquilinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inquilinos")
@RequiredArgsConstructor
public class InquilinoController {

    private final InquilinoService service;

    @PostMapping
    public ResponseEntity<InquilinoResponse> criar(@Valid @RequestBody InquilinoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<InquilinoResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InquilinoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InquilinoResponse> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody InquilinoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
