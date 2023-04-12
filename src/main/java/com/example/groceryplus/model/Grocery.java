package com.example.groceryplus.model;

public class Grocery {
    private String name;
    private double amount;
    private String unit;
    private double cartAmount;

    public Grocery(String name, double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.cartAmount = 0;
    }

    // Konstruktør nr 2 til brug når cartAmount skal inkluderes.
    public Grocery(String name, double amount, String unit, double cartAmount) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.cartAmount = cartAmount;
    }

    public Grocery() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCartAmount() {
        return cartAmount;
    }

    public void setCartAmount(double cartAmount) {
        this.cartAmount = cartAmount;
    }

    @Override
    public String toString() {
        return "Grocery{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }
}
