package com.kirana.kirana_register.service.helper.staff;

import com.kirana.kirana_register.dao.postgres.InventoryDao;
import com.kirana.kirana_register.entity.postgres.Inventory;
import org.springframework.stereotype.Service;


@Service
public class InventoryManagementService {

    private  final InventoryDao inventoryDao;

    public InventoryManagementService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public void reduceStock(Long inventoryId, int quantity) {

        Inventory inventory =inventoryDao
                .findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryDao.save(inventory);
    }

    public Inventory createInventory(int capacity, int quantity) {
        Inventory inventory = new Inventory();
        inventory.setCapacity(capacity);
        inventory.setQuantity(quantity);

        return inventoryDao.save(inventory);
    }
}

