package com.kirana.kirana_register.dto.request;

public class ProductItemRequestDTO {

    private String productId;
    private int quantity;


    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

