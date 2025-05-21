package com.gevernova.OnlineOrderProcessing;

public class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
