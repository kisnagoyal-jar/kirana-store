package com.kirana.kirana_register.dao.mongodb;

import com.kirana.kirana_register.entity.mongodb.KiraanaStore;
import com.kirana.kirana_register.repository.mongodb.KiraanaStoreRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KiraanaStoreDao {

    private final KiraanaStoreRepository kiraanaStoreRepository;

    public KiraanaStoreDao(KiraanaStoreRepository kiraanaStoreRepository) {
        this.kiraanaStoreRepository = kiraanaStoreRepository;
    }

    public Optional<KiraanaStore> findById(String id) {
        return kiraanaStoreRepository.findById(id);
    }

    public KiraanaStore save(KiraanaStore kirana) {
        return kiraanaStoreRepository.save(kirana);
    }
}
