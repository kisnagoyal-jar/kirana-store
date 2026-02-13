package com.kirana.kirana_register.service.helper.staff;

import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.entity.postgres.Inventory;
import com.kirana.kirana_register.repository.mongodb.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCreationService {

    private final ProductRepository productRepository;
    private final InventoryManagementService inventoryService;

    public ProductCreationService(ProductRepository productRepository,
                                    InventoryManagementService inventoryService) {
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
    }

    public Product createProduct(String kiraanaId,
                                 String name,
                                 int capacity, int price, int quantity) {

        Inventory inventory = inventoryService.createInventory(capacity,quantity);

        Product product = new Product();
        product.setKiraanaId(kiraanaId);
        product.setProductName(name);
        product.setInventoryId(inventory.getId());

        return productRepository.save(product);
    }
}
