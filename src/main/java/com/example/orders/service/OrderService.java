package com.example.orders.service;

import com.example.orders.audit.OrderAudit;
import com.example.orders.config.OrderApiProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderAudit orderAudit;
   private  final OrderApiProperties orderApiProperties;
    public OrderService(@Qualifier("fileAudit") OrderAudit orderAudit,
    OrderApiProperties orderApiProperties) {
        this.orderAudit = orderAudit;
        this.orderApiProperties = orderApiProperties;
    }

    public String describe() {
        return "OrderService operationnel";
    }

    public void process() {
        orderAudit.record("Traitement d'une commande");
    }

    @PostConstruct
    void initialize(){
        System.out.println("OrderService initialization");
    }

    public void displayOrderApiConfiguration(){
        System.out.println("Taille par défaut : " + orderApiProperties.defaultPageSize());
    }
}
