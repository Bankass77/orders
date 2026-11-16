package com.example.orders.adapter;

import com.example.orders.application.port.PaymentGatewayPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("transfert")
public class FakeTransfertGateway implements PaymentGatewayPort {
    @Override
    public String change(UUID orderId) {
        return "TFR-"+ orderId;
    }
}
