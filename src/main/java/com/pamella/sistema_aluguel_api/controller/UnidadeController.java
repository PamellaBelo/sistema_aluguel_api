package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.UnidadeRequest;
import com.pamella.sistema_aluguel_api.dto.UnidadeResponse;
import com.pamella.sistema_aluguel_api.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService service;

    @PostMapping
    public ResponseEntity<UnidadeResponse> criar(@RequestBody UnidadeRequest request) {
        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<UnidadeResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
