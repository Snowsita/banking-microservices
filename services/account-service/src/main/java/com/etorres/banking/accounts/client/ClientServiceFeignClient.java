package com.etorres.banking.accounts.client;

import com.etorres.banking.accounts.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service", url = "http://client-service:8080/api/v1/clients")
public interface ClientServiceFeignClient {

    @GetMapping("/{clientId}")
    ClientDTO getClientById(@PathVariable("clientId") String clientId);
}
