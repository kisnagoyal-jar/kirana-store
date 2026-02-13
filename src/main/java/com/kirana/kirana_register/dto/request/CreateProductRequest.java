package com.kirana.kirana_register.dto.request;

public class CreateProductRequest {

    private String productName;
    private double price;
    private Long inventoryId;

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
}
