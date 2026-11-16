package com.example.orders.audit;

public class OrderAuditLogger {
    public void log(String message){
        System.out.println("[Log Audit] " + message);
    }
}
