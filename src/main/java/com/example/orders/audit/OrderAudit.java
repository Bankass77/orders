package com.example.orders.audit;

public interface OrderAudit {
    void record(String message);
}
