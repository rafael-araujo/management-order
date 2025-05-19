package com.hexagonal.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.hexagonal.template",
		"com.hexagonal.template.infrastructure.adapter.repository",
		"com.hexagonal.template.infrastructure.external.persistence.entity",
		"com.hexagonal.template.infrastructure.internal",
		"com.hexagonal.template.domain.model",
		"com.hexagonal.template.application"})
public class OrderManagementlApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementlApplication.class, args);
	}

}
