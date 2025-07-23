package com.talant.bootcamp.model;

public class Invoice {
    private String id;
    private double amount;
    private String customer;

    public Invoice() {}

    public Invoice(String id, double amount, String customer) {
        this.id = id;
        this.amount = amount;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
