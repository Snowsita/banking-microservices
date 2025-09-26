package com.etorres.banking.clients.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "persona_id")
public class Client extends Person {

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status;
}
