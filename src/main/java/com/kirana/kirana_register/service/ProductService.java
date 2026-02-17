package com.kirana.kirana_register.service;

import com.kirana.kirana_register.dao.mongodb.ProductDao;
import com.kirana.kirana_register.dao.postgres.InventoryDao;
import com.kirana.kirana_register.dto.request.CreateProductRequest;
import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.entity.postgres.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final InventoryDao inventoryDao;
    private final ProductDao productDao;

    public ProductService(
            InventoryDao inventoryDao,
            ProductDao productDao
    ) {
        this.inventoryDao = inventoryDao;
        this.productDao = productDao;
    }

    public String createProduct(
            CreateProductRequest request,
            String kiranaId
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
            product.setKiranaId(kiranaId);

            productDao.save(product);

            return product.getId();

        } catch (Exception ex) {
            // 🔁 compensate
            inventoryDao.deleteById(inventory.getId());
            throw ex;
        }
    }

    public List<Product> getProductsForKiraana(String kiranaId) {
        return productDao.findByKiranaId(kiranaId);
    }
}
