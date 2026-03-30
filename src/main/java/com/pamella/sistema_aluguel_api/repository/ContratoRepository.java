package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByUnidadeIdAndAtivoTrue(Long unidadeId);
    boolean existsByUnidadeIdAndAtivoTrue(Long unidadeId);
    boolean existsByInquilinoIdAndAtivoTrue(Long inquilinoId);
    long countByAtivoTrue();

    @Query("SELECT COALESCE(SUM(c.valorAluguel), 0) FROM Contrato c WHERE c.ativo = true")
    BigDecimal somarValorAluguelAtivos();
}
