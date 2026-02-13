package com.kirana.kirana_register.controller.admin;

import com.kirana.kirana_register.entity.postgres.Inventory;
import com.kirana.kirana_register.dao.postgres.InventoryDao;
import com.kirana.kirana_register.dto.request.CreateInventoryRequest;
import com.kirana.kirana_register.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/inventory")
@PreAuthorize("hasRole('ADMIN')")
public class InventoryController {

    private final InventoryDao inventoryDao;

    public InventoryController(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    @PostMapping
    public ResponseEntity<?> createInventory(
            @AuthenticationPrincipal UserPrincipal admin,
            @RequestBody CreateInventoryRequest request
    ) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(request.getQuantity());

        Inventory saved = inventoryDao.save(inventory);

        return ResponseEntity.ok(
                Map.of(
                        "inventoryId", saved.getId(),
                        "quantity", saved.getQuantity()
                )
        );
    }
}
