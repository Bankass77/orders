package com.example.orders.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTransactionDemo {
    public void outer() {

        orderTransactionWorker.process();

    }

    /*
    @Transactional
    public void inner() {
        System.out.println("Methode inner");
    }*/

    private final OrderTransactionWorker orderTransactionWorker;

    public OrderTransactionDemo(OrderTransactionWorker orderTransactionWorker) {
        this.orderTransactionWorker = orderTransactionWorker;
    }

}
