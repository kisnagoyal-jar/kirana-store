package com.kirana.kirana_register.repository.mongodb;

import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByPhoneNumberAndKiraanaId(String phoneNumber,String kiraanaId);

    boolean existsByPhoneNumber(String phone);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByRole(Roles role);
}
