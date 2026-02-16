package com.kirana.kirana_register.service.staff;

import com.kirana.kirana_register.dao.mongodb.ProductDao;
import com.kirana.kirana_register.dao.postgres.InventoryDao;
import com.kirana.kirana_register.dto.request.CreateProductRequest;
import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.entity.postgres.Inventory;
import org.springframework.stereotype.Service;

@Service
public class ProductCreationService {

    private final InventoryDao inventoryDao;
    private final ProductDao productDao;

    public ProductCreationService(
            InventoryDao inventoryDao,
            ProductDao productDao
    ) {
        this.inventoryDao = inventoryDao;
        this.productDao = productDao;
    }

    public String createProduct(
            CreateProductRequest request,
            String kiraanaId
    ) {

        // ✅ validations
        if (request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        if (request.getInitialQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (request.getInitialQuantity() > request.getCapacity()) {
            throw new IllegalArgumentException(
                    "Initial quantity cannot exceed capacity"
            );
        }

        // 1️⃣ create inventory (Postgres)
        Inventory inventory = new Inventory();
        inventory.setCapacity(request.getCapacity());
        inventory.setQuantity(request.getInitialQuantity());

        inventory = inventoryDao.save(inventory);

        try {
            // 2️⃣ create product (MongoDB)
            Product product = new Product();
            product.setProductName(request.getProductName());
            product.setPrice(request.getPrice());
            product.setInventoryId(inventory.getId());
            product.setKiraanaId(kiraanaId);

            productDao.save(product);

            return product.getId();

        } catch (Exception ex) {
            // 🔁 compensate
            inventoryDao.deleteById(inventory.getId());
            throw ex;
        }
    }
}
