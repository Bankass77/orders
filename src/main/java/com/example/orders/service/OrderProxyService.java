package com.example.orders.service;

import org.springframework.stereotype.Service;

@Service
public class OrderProxyService {
    public void processOrder(){
        System.out.println("OrderProxyService processOrder");
    }
}
