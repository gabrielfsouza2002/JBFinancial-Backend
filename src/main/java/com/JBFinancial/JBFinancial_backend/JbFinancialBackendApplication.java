package com.JBFinancial.JBFinancial_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JbFinancialBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JbFinancialBackendApplication.class, args);
	}

}
