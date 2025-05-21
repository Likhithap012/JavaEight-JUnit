package com.gevernova.OnlineOrderProcessing;

import java.util.List;
import java.util.Optional;

public class Order {
    private User user;
    private List<Item> items;
    private String paymentMethod;
    private String deliveryAddress;
    private Optional<String> promoCode;

    public Order(User user, List<Item> items, String paymentMethod, String deliveryAddress, String promoCode) {
        this.user = user;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.promoCode = Optional.ofNullable(promoCode);
    }

    // Getters
    public User getUser() { return user; }
    public List<Item> getItems() { return items; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public Optional<String> getPromoCode() { return promoCode; }
}

