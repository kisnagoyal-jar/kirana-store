package com.kirana.kirana_register.service.staff;

import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.repository.mongodb.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductValidationService {

    private final ProductRepository productRepository;

    public ProductValidationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product validateProduct(String productId,String kiraanaId) {

        Product product = productRepository
                .findByIdAndKiraanaId(productId, kiraanaId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Product not found for this kirana"
                        )
                );

        if (product.getInventoryId() == null) {
            throw new IllegalStateException("Product has no inventory");
        }

        return product;
    }
}
