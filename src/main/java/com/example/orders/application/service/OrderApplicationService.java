package com.example.orders.application.service;

import com.example.orders.application.port.PaymentGatewayPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderApplicationService {

    private final PaymentGatewayPort paymentGatewayPort;

    public OrderApplicationService(@Qualifier("card") PaymentGatewayPort paymentGatewayPort) {
        this.paymentGatewayPort = paymentGatewayPort;
    }
}
