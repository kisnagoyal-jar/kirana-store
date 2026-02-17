package com.kirana.kirana_register.entity.postgres;


import com.github.f4b6a3.ulid.UlidCreator;
import com.kirana.kirana_register.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="transactions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @Column(length = 26, nullable = false, updatable = false)
    private String id;

    @Column(name = "kirana_id", nullable = false)
    private String kiranaId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private TransactionType type;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "original_transaction_id")
    private String originalTransactionId;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    public void generateId(){
        this.id = UlidCreator.getUlid().toString();
    }


}
