package com.pamella.sistema_aluguel_api.controller;


import com.pamella.sistema_aluguel_api.model.Pagamento;
import com.pamella.sistema_aluguel_api.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public Pagamento salvar(@RequestBody Pagamento pagamento) {
        return pagamentoService.registrarPagamento(pagamento);
    }

    @GetMapping
    public List<Pagamento> listarTodos() {
        return pagamentoService.buscarTodosPagamentos();
    }

    @GetMapping("/{id}")
    public Pagamento buscarPorId(@PathVariable Long id) {
        return pagamentoService.buscarPagamentoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        pagamentoService.excluirPagamento(id);
    }
}

