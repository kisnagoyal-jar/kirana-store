package com.kirana.kirana_register.entity.postgres;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "inventories")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Inventory {

    @Id
    @Column(length = 26, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, updatable = false)
    private int capacity;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        // Generate ULID only if not set
        if (this.id == null) {
            this.id = UlidCreator.getUlid().toString();
        }

        // Default capacity = 50
        if (this.capacity == 0) {
            this.capacity = 50;
        }
    }
}
