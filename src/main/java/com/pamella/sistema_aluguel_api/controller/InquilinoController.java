package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.model.Inquilino;
import com.pamella.sistema_aluguel_api.service.InquilinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inquilinos")
public class InquilinoController {

    @Autowired
    private InquilinoService inquilinoService;

    @GetMapping
    public List<Inquilino> getAll() {
        return inquilinoService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Inquilino> getById(@PathVariable Long id) {
        return inquilinoService.getById(id);
    }

    @PostMapping
    public Inquilino create(@RequestBody Inquilino inquilino) {
        return inquilinoService.create(inquilino);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        inquilinoService.delete(id);
    }
}
