package com.kirana.kirana_register.service;


import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.dto.request.UserRequestDTO;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.security.password.PasswordService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;
    private final PasswordService passwordService;

    public UserService(UserDao userDao, PasswordService passwordService) {
        this.userDao = userDao;
        this.passwordService = passwordService;
    }

    public User createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setPhoneNumber(userRequestDTO.getPhoneNumber());
        user.setKiranaId(userRequestDTO.getKiranaId());
        user.setRole(userRequestDTO.getRole());
        user.setPassword(passwordService.hash(userRequestDTO.getPassword()));
        user.setStatus(userRequestDTO.getStatus());

        return userDao.save(user);
    }

    public User updateUser(String userId, UserRequestDTO customerInfo){
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(customerInfo.getName());
        user.setPhoneNumber(customerInfo.getPhoneNumber());
        user.setKiranaId(customerInfo.getKiranaId());
        user.setRole(customerInfo.getRole());
        user.setPassword(customerInfo.getPassword());
        user.setStatus(customerInfo.getStatus());

        return userDao.save(user);
    }

    public User getUser(String id) {
        return userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
}
