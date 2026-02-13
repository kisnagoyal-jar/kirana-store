package com.kirana.kirana_register.entity.postgres;


import com.kirana.kirana_register.enums.TransactionType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="transactions")
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;




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

    public Long getOriginalTransactionId() {
        return originalTransactionId;
    }

}
