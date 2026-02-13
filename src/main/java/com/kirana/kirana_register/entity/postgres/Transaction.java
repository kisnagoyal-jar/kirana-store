package com.kirana.kirana_register.entity.postgres;


import com.kirana.kirana_register.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kiraana_id", nullable = false)
    private String kiraanaId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private  double exchangeRate;


    @Column
    private Long originalTransactionId;

    @Column
    private boolean isCompleted;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private  LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setKiraanaId(String kiraanaId) {
        this.kiraanaId = kiraanaId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setOriginalTransactionId(Long originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }


    public Long getId() {
        return id;
    }

    public String getKiraanaId() {
        return kiraanaId;
    }

    public String getUserId() {
        return userId;
    }

    public TransactionType getType() {
        return type;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public Long getOriginalTransactionId() {
        return originalTransactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
