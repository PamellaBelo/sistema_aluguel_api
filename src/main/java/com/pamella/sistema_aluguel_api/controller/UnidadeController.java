package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.UnidadeRequest;
import com.pamella.sistema_aluguel_api.dto.UnidadeResponse;
import com.pamella.sistema_aluguel_api.model.StatusUnidade;
import com.pamella.sistema_aluguel_api.service.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;

    @PostMapping
    public ResponseEntity<UnidadeResponse> criar(@RequestBody @Valid UnidadeRequest request) {
        return ResponseEntity.ok(unidadeService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeResponse>> listar() {
        return ResponseEntity.ok(unidadeService.listar());
    }

    @GetMapping("/imovel/{imovelId}")
    public ResponseEntity<List<UnidadeResponse>> listarPorImovel(@PathVariable Long imovelId) {
        return ResponseEntity.ok(unidadeService.listarPorImovel(imovelId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UnidadeResponse>> listarPorStatus(@PathVariable StatusUnidade status) {
        return ResponseEntity.ok(unidadeService.listarPorStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeResponse> atualizar(@PathVariable Long id, @RequestBody @Valid UnidadeRequest request) {
        return ResponseEntity.ok(unidadeService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        unidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}