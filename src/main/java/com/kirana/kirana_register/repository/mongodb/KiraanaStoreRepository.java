package com.kirana.kirana_register.repository.mongodb;

import com.kirana.kirana_register.entity.mongodb.KiraanaStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KiraanaStoreRepository extends MongoRepository<KiraanaStore,String> {
}
