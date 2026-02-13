package com.kirana.kirana_register.dto.request;

public class CreateProductRequest {

    private String productName;
    private double price;

    private int initialQuantity;
    private int capacity;

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getInitialQuantity() {
        return initialQuantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInitialQuantity(int initialQuantity) {
        this.initialQuantity = initialQuantity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
