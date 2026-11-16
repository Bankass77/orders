package com.example.orders.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTransactionWorker {

    @Transactional
    public void process(){
  System.out.println("Traitement transactionnel.");
    }
}
