package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.dto.ImovelRequest;
import com.pamella.sistema_aluguel_api.dto.ImovelResponse;
import com.pamella.sistema_aluguel_api.service.ImovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imoveis")
@RequiredArgsConstructor
public class ImovelController {

    private final ImovelService imovelService;

    @PostMapping
    public ResponseEntity<ImovelResponse> criar(@RequestBody @Valid ImovelRequest request) {
        return ResponseEntity.ok(imovelService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ImovelResponse>> listar() {
        return ResponseEntity.ok(imovelService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        imovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<ImovelResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ImovelRequest request) {
        return ResponseEntity.ok(imovelService.atualizar(id, request));
    }
}