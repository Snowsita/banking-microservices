package com.etorres.banking.accounts.repository;

import com.etorres.banking.accounts.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {


    List<Movement> findByAccount_ClientIdAndDateBetweenOrderByDateAsc(String clientId, LocalDateTime startDate, LocalDateTime endDate);
}
