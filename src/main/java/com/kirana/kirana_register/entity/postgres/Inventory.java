package com.kirana.kirana_register.entity.postgres;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column
    private int capacity; // todo: capacity is fixed so how can we handle this ?

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
}
