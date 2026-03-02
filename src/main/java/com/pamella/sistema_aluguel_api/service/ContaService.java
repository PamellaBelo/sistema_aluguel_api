package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.model.Conta;
import com.pamella.sistema_aluguel_api.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public List<Conta> buscarContasPorContrato(Long contratoId) {
        return contaRepository.findByCasaUnidadeContratoId(contratoId);
    }

    public List<Conta> buscarContasPorCasa(Long casaId) {
        return contaRepository.findByCasaId(casaId);
    }

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }

    public void delete(Long id) {
        contaRepository.deleteById(id);
    }
}
