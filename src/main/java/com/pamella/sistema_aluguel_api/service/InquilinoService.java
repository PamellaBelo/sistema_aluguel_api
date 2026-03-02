package com.pamella.sistema_aluguel_api.service;

import com.pamella.sistema_aluguel_api.model.Inquilino;
import com.pamella.sistema_aluguel_api.repository.InquilinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InquilinoService {

    @Autowired
    private InquilinoRepository inquilinoRepository;

    public List<Inquilino> getAll() {
        return inquilinoRepository.findAll();
    }
    public Optional<Inquilino> getById(Long id) {
        return inquilinoRepository.findById(id);
    }

    public Inquilino create(Inquilino inquilino) {
        return inquilinoRepository.save(inquilino);
    }

    public void delete(Long id) {
        inquilinoRepository.deleteById(id);
    }


}
