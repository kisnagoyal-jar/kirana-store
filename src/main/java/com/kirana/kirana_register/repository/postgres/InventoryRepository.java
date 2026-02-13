package com.kirana.kirana_register.repository.postgres;

import com.kirana.kirana_register.entity.postgres.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

}
