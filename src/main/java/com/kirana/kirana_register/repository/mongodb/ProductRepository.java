package com.kirana.kirana_register.repository.mongodb;

import com.kirana.kirana_register.entity.mongodb.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findByIdAndKiranaId(String id, String kiranaId);

    List<Product> findByKiranaId(String kiranaId);
}
