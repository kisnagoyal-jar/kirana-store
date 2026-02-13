package com.kirana.kirana_register.dao.mongodb;

import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.repository.mongodb.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductDao {

    private final ProductRepository productRepository;

    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findByIdAndKiraanaId(
            String productId,
            String kiraanaId
    ) {
        return productRepository.findByIdAndKiraanaId(productId, kiraanaId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
