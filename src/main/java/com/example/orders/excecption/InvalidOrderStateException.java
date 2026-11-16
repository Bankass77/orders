package com.example.orders.excecption;

public class InvalidOrderStateException extends IllegalArgumentException {
    public InvalidOrderStateException(String message) {
        super(message);
    }
}
