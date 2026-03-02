package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByCasaUnidadeContratoId(Long contratoId);
    List<Conta> findByCasaId(Long casaId);

}
