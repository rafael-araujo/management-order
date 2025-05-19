package com.order.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.order.management",
		"com.order.management.infrastructure.adapter.repository",
		"com.order.management.infrastructure.external.persistence.entity",
		"com.order.management.infrastructure.internal",
		"com.order.management.domain.model",
		"com.order.management.application"})
public class OrderManagementlApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementlApplication.class, args);
	}

}
