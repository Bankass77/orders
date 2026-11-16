package com.example.orders.application.service;

import com.example.orders.application.port.PaymentGatewayPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderApplicationServiceTest {

    @Test
    void service_is_built_without_spring_with_a_fake_gateway() {

        PaymentGatewayPort fake = orderId -> "FAKE" + orderId;
        var service = new OrderApplicationService(fake);
        Assertions.assertThat(service).isNotNull();
    }

}