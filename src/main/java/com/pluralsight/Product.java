package com.pluralsight;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private int stock;

    public Product(int id, String name, double unitPrice, int stock) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return String.format("| %-6d | %-25s | %-10.2f | %-10d |", id, name, unitPrice, stock);
    }
}
