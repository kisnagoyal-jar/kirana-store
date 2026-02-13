package com.kirana.kirana_register.service.helper.admin;

import com.kirana.kirana_register.entity.mongodb.KiraanaStore;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import com.kirana.kirana_register.repository.mongodb.KiraanaStoreRepository;
import com.kirana.kirana_register.repository.mongodb.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class KiraanaStoreService {

    private final KiraanaStoreRepository storeRepository;
    private final UserRepository userRepository;

    public KiraanaStoreService(KiraanaStoreRepository storeRepository,
                               UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    public String createKiraanaStore(String name, String location,
                                     String adminName, String phone, String password) {

        KiraanaStore store = new KiraanaStore();
        store.setName(name);
        store.setLocation(location);

        store = storeRepository.save(store);

        User admin = new User();
        admin.setName(adminName);
        admin.setPhoneNumber(phone);
        admin.setPassword(password); // hash later
        admin.setRole(Roles.ADMIN);
        admin.setStatus(Status.ACTIVE);
        admin.setKiraanaId(store.getId());

        userRepository.save(admin);

        return store.getId();
    }
}

