package com.etorres.banking.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	private static final Logger log = LoggerFactory.getLogger(ApiGatewayApplication.class);

	public static void main(String[] args) {
		log.info("INFO: Iniciando API Gateway");
		SpringApplication.run(ApiGatewayApplication.class, args);
		log.info("INFO: API Gateway iniciado exitosamente");
	}

}
