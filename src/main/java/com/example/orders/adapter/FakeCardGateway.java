package com.example.orders.adapter;

import com.example.orders.application.port.PaymentGatewayPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("card")
public class FakeCardGateway implements PaymentGatewayPort {
    @Override
    public String change(UUID orderId) {
        return  "CARD-" + orderId;
    }
}
