package com.kirana.kirana_register.service;

import com.kirana.kirana_register.dao.mongodb.KiranaStoreDao;
import com.kirana.kirana_register.entity.mongodb.KiranaStore;
import org.springframework.stereotype.Service;

@Service
public class kiranaStoreService {
    private final KiranaStoreDao kiranaStoreDao;

    public kiranaStoreService(KiranaStoreDao kiranaStoreDao) {
        this.kiranaStoreDao = kiranaStoreDao;
    }

    public KiranaStore createKiranaStore(KiranaStore store) {
        // Logic to save the store to the database
        return kiranaStoreDao.save(store);
    }

    public KiranaStore updateKiranaStore(String storeId, KiranaStore updatedStore) {
        return kiranaStoreDao.update(storeId, updatedStore);
    }


}
