package com.etorres.banking.accounts.repository;

import com.etorres.banking.accounts.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    /**
     * Encuentra la cuenta según el número de cuenta.
     *
     * @param accountNumber el número de cuenta a buscar
     * @return Una cuenta envuelta en un Optional si se encuentra, o un Optional vacío si no se encuentra.
     */
    Optional<Cuenta> findByAccountNumber(String accountNumber);
}
