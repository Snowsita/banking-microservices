package com.etorres.banking.accounts.repository;

import com.etorres.banking.accounts.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {


    List<Movimiento> findByAccount_ClientIdAndDateBetweenOrderByDateAsc(String clientId, LocalDateTime startDate, LocalDateTime endDate);
}
