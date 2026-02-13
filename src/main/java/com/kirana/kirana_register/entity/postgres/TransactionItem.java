package com.kirana.kirana_register.entity.postgres;


import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="transaction_tems")
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;


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

    public Date getCreatedAt() {
        return createdAt;
    }
}
