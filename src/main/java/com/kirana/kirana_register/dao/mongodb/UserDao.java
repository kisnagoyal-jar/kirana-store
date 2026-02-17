package com.kirana.kirana_register.dao.mongodb;

import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.repository.mongodb.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDao {

    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByPhoneNumberAndKiranaId(
            String phoneNumber,
            String kiranaId
    ) {
        return userRepository.findByPhoneNumberAndKiranaId(phoneNumber, kiranaId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }
}


