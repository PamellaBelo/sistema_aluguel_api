package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {}
