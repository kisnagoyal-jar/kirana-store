package com.kirana.kirana_register.controller.admin;

import com.kirana.kirana_register.dao.mongodb.ProductDao;
import com.kirana.kirana_register.dto.request.CreateProductRequest;
import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @AuthenticationPrincipal UserPrincipal admin,
            @RequestBody CreateProductRequest request
    ) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setInventoryId(request.getInventoryId());
        product.setKiraanaId(admin.getUser().getKiraanaId());

        Product saved = productDao.save(product);

        return ResponseEntity.ok(
                Map.of(
                        "productId", saved.getId(),
                        "productName", saved.getProductName()
                )
        );
    }
}
