package com.kirana.kirana_register.entity.postgres;


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

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;

}
