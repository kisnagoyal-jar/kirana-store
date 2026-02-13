package com.kirana.kirana_register.entity.mongodb;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String kiraanaId;
    private Long inventoryId;
    private String productName;

    // all prices are stored in USD for consistency, conversion is done at the service layer
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void setKiraanaId(String kiraanaId) {
        this.kiraanaId = kiraanaId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getId() {
        return id;
    }

    public String getKiraanaId() {
        return kiraanaId;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


}
