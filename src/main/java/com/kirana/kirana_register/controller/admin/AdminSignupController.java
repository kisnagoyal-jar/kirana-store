package com.kirana.kirana_register.controller.admin;

import com.kirana.kirana_register.dao.mongodb.KiraanaStoreDao;
import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.dto.request.AdminSignupRequest;
import com.kirana.kirana_register.entity.mongodb.KiraanaStore;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AdminSignupController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final KiraanaStoreDao kiranaDao;

    public AdminSignupController(
            UserDao userDao,
            PasswordEncoder passwordEncoder,
            KiraanaStoreDao kiranaDao
    ) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.kiranaDao = kiranaDao;
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signupAdmin(
            @RequestBody AdminSignupRequest request
    ) {
        // 1️⃣ Ensure admin phone not already used
        if (userDao.existsByPhoneNumber(request.getAdminPhone())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Phone already registered"));
        }

        // 2️⃣ Create Kirana
        KiraanaStore kirana = new KiraanaStore();
        kirana.setName(request.getKiranaName());
        kirana.setLocation(request.getKiranaAddress());

        kirana = kiranaDao.save(kirana);

        // 3️⃣ Create Admin
        User admin = new User();
        admin.setName(request.getAdminName());
        admin.setPhoneNumber(request.getAdminPhone());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Roles.ADMIN);
        admin.setStatus(Status.ACTIVE);
        admin.setKiraanaId(kirana.getId());

        userDao.save(admin);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Admin and Kirana created successfully",
                        "kiraanaId", kirana.getId()
                )
        );
    }
}
