package com.kirana.kirana_register.dao.mongodb;

import com.kirana.kirana_register.entity.mongodb.KiranaStore;
import com.kirana.kirana_register.repository.mongodb.KiranaStoreRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KiranaStoreDao {

    private final KiranaStoreRepository kiranaStoreRepository;

    public KiranaStoreDao(KiranaStoreRepository kiranaStoreRepository) {
        this.kiranaStoreRepository = kiranaStoreRepository;
    }

    public Optional<KiranaStore> findById(String id) {
        return kiranaStoreRepository.findById(id);
    }

    public KiranaStore save(KiranaStore kirana) {
        return kiranaStoreRepository.save(kirana);
    }

    public KiranaStore update(String storeId, KiranaStore updatedStore) {
        Optional<KiranaStore> existingStoreOpt = kiranaStoreRepository.findById(storeId);
        if (existingStoreOpt.isPresent()) {
            KiranaStore existingStore = existingStoreOpt.get();
            existingStore.setName(updatedStore.getName());
            existingStore.setLocation(updatedStore.getLocation());
            existingStore.setStatus(updatedStore.getStatus());
            return kiranaStoreRepository.save(existingStore);
        } else {
            throw new RuntimeException("Kirana Store not found with id: " + storeId);
        }
    }
}
