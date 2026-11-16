package com.example.orders.audit;

import org.springframework.stereotype.Component;

@Component("fileAudit")
public class FileOrderAudit implements OrderAudit {
    @Override
    public void record(String message) {
        System.out.println("[ FILE ORDER AUDIT ] " + message);
    }
}
