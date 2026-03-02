package com.pamella.sistema_aluguel_api.controller;

import com.pamella.sistema_aluguel_api.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contratos")
public class ContratoController {
    @Autowired
    private ContratoService contratoService;

    @GetMapping("/{id}/total-mensal")
    public BigDecimal calcularTotalMensal(@PathVariable Long id) {
        return contratoService.calcularTotalMensal(id);
    }
}
