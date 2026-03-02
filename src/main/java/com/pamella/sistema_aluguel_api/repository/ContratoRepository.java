package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByCasaId(Long casaId);
    Optional<Contrato> findByCasaIdAndDataFimIsNull(Long casaId);
}
