package com.example.orders.domain;

public record OrderLine(String sku, int quantity) {

    public OrderLine {
        if (sku == null || sku.isBlank()) {

            throw new IllegalArgumentException("sku required");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
    }
}
