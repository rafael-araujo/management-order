package com.order.management;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

@ExtendWith(MockitoExtension.class)
class OrderManagementlApplicationTest {

    @Test
    void contextLoads() {

    }

    @Test
    void mainMethodRunsSuccessfully() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
            OrderManagementlApplication.main(new String[]{});
            mockedSpringApplication.verify(() -> SpringApplication.run(OrderManagementlApplication.class, new String[]{}));
        }
    }
}