package com.kirana.kirana_register.dao.postgres;

import com.kirana.kirana_register.entity.postgres.Inventory;
import com.kirana.kirana_register.repository.postgres.InventoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InventoryDao {

    private final InventoryRepository inventoryRepository;

    public InventoryDao(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<Inventory> findById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId);
    }

    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }


    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}
