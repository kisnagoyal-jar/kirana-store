package com.kirana.kirana_register.bootstrap;

import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrapService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrapService(UserDao userDao,
                                 PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createAdminIfNotExists() {

        if (userDao.existsByRole(Roles.ADMIN)) {
            return;
        }

        User admin = new User();
        admin.setName("System Admin");
        admin.setPhoneNumber("9999999999");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Roles.ADMIN);
        admin.setStatus(Status.ACTIVE);
        admin.setKiraanaId("K1");

        userDao.save(admin);

        System.out.println("✅ DEFAULT ADMIN CREATED");
    }
}
