package com.pamella.sistema_aluguel_api.controller;


import com.pamella.sistema_aluguel_api.dto.CasaRequest;
import com.pamella.sistema_aluguel_api.dto.CasaResponse;
import com.pamella.sistema_aluguel_api.service.CasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casas")
@RequiredArgsConstructor
public class CasaController {

    private final CasaService casaService;

    @PostMapping
    public ResponseEntity<CasaResponse> criar(@RequestBody CasaRequest request){
        return ResponseEntity.ok(casaService.criar(request));
    }

    @GetMapping("/unidade/{unidadeId}")
    public ResponseEntity<List<CasaResponse>> listaPorUnidade(@PathVariable Long unidadeId){
        return ResponseEntity.ok(casaService.listarPorUnidade(unidadeId));
    }

    @GetMapping
    public ResponseEntity<List<CasaResponse>> listarTodas() {
        return ResponseEntity.ok(casaService.listarTodas());
    }
}
