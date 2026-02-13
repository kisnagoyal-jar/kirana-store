package com.kirana.kirana_register.entity.postgres;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transaction_tems")
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long transactionId;


    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public Long getId() {
        return id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
