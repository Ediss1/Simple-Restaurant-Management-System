package com.EDISKASUMOVICipia.example;

import java.util.List;

public class Order {
    private int orderId;
    private User user;
    private List<Food> items;
    private double totalPrice;

    public Object getUser() {
        return user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Constructor, getters, and setters
}
