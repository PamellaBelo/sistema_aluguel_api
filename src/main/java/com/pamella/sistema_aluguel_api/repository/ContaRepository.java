package com.pamella.sistema_aluguel_api.repository;

import com.pamella.sistema_aluguel_api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByContratoId(Long contratoId); // ✅ único método necessário

    @Query("SELECT COUNT(c) FROM Conta c WHERE c.vencimento < :hoje")
    long countContasVencidas(LocalDate hoje);
}
