package com.kirana.kirana_register.controller.admin;

import com.kirana.kirana_register.dto.request.CreateProductRequest;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.service.helper.staff.ProductCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {

    private final ProductCreationService productCreationService;

    public ProductController(ProductCreationService productCreationService) {
        this.productCreationService = productCreationService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @AuthenticationPrincipal UserPrincipal admin,
            @RequestBody CreateProductRequest request
    ) {
        String productId = productCreationService.createProduct(
                request,
                admin.getUser().getKiraanaId()
        );

        return ResponseEntity.ok(
                Map.of(
                        "productId", productId,
                        "status", "PRODUCT_CREATED"
                )
        );
    }
}
