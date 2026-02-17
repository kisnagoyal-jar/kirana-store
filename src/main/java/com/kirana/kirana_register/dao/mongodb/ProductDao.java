package com.kirana.kirana_register.dao.mongodb;

import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.repository.mongodb.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {

    private final ProductRepository productRepository;

    public ProductDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findByIdAndKiranaId(
            String productId,
            String kiranaId
    ) {
        return productRepository.findByIdAndKiranaId(productId, kiranaId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findByKiranaId(String kiranaId) {
        return productRepository.findByKiranaId(kiranaId);
    }
}
