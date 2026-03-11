package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Pagamento;
import com.pamella.sistema_aluguel_api.model.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByContratoId(Long contratoId);

    // ✅ Evita pagamento duplicado para o mesmo mês
    Optional<Pagamento> findByContratoIdAndMesReferencia(Long contratoId, LocalDate mesReferencia);

    List<Pagamento> findByStatus(StatusPagamento status);
}