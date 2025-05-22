package com.gevernova.OnlineOrderProcessing;

import java.util.function.Predicate;

public class OrderService {

    Predicate<Order> isPaymentValid = order -> order.getPaymentMethod() != null && !order.getPaymentMethod().isBlank();
    Predicate<Order> isAddressValid = order -> order.getDeliveryAddress() != null && order.getDeliveryAddress().length() > 5;

    public void validateOrder(Order order) throws InvalidPaymentException, InvalidAddressException {
        if (!isPaymentValid.test(order)) {
            throw new InvalidPaymentException("Invalid payment method");
        }
        if (!isAddressValid.test(order)) {
            throw new InvalidAddressException("Invalid delivery address");
        }

    }
}

