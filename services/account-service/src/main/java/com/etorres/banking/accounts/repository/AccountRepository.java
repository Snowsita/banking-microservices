package com.etorres.banking.accounts.repository;

import com.etorres.banking.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Encuentra la cuenta según el número de cuenta.
     *
     * @param accountNumber el número de cuenta a buscar
     * @return Una cuenta envuelta en un Optional si se encuentra, o un Optional vacío si no se encuentra.
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Encuentra todas las cuentas asociadas a un ID de cliente específico.
     *
     * @param clientId el ID del cliente cuyas cuentas se desean buscar
     * @return Una lista de cuentas asociadas al ID del cliente proporcionado
     */
    List<Account> findByClientId(String clientId);
}
