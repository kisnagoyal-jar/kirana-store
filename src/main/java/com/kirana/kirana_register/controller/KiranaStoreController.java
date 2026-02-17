package com.kirana.kirana_register.controller;

import com.kirana.kirana_register.entity.mongodb.KiranaStore;
import com.kirana.kirana_register.service.kiranaStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/kirana-stores")
public class KiranaStoreController {

    private final kiranaStoreService kiranaStoreService;

    public KiranaStoreController(kiranaStoreService kiranaStoreService) {
        this.kiranaStoreService = kiranaStoreService;
    }

    @PostMapping("/")
    public ResponseEntity<KiranaStore> createKiranaStore(KiranaStore store) {
        return ResponseEntity.ok(kiranaStoreService.createKiranaStore(store));

    }
}
