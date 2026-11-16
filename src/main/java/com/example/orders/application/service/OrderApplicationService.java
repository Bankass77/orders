package com.example.orders.application.service;

import com.example.orders.application.port.PaymentGatewayPort;
import com.example.orders.domain.Order;
import com.example.orders.domain.OrderLine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderApplicationService {

    private final PaymentGatewayPort paymentGatewayPort;

    public OrderApplicationService(@Qualifier("card") PaymentGatewayPort paymentGatewayPort) {
        this.paymentGatewayPort = paymentGatewayPort;
    }

    public Order createOrder(List<OrderLine> orderLineList) {
        throw  new UnsupportedOperationException("jour 5");
    }
}
