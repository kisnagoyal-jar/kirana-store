package com.kirana.kirana_register.entity.postgres;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int capacity; // todo: capacity is fixed so how can we handle this ?

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
