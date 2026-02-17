package com.kirana.kirana_register.controller;

import com.kirana.kirana_register.dto.request.CreateProductRequest;
import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(
            @AuthenticationPrincipal UserPrincipal admin,
            @RequestBody CreateProductRequest request
    ) {
        String productId = productService.createProduct(
                request,
                admin.getUser().getKiranaId()
        );

        return ResponseEntity.ok(
                Map.of(
                        "productId", productId,
                        "status", "PRODUCT_CREATED"
                )
        );
    }

    // TODO: I will implement pagination
    @GetMapping("{kiranaId}")
    public List<Product> getAllProductsForKiraana(@PathVariable String kiraanaId) {
        return productService.getProductsForKiraana(kiraanaId);
    }
}
