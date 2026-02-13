package com.kirana.kirana_register.controller.admin;

import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.dto.request.CreateStaffRequest;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import com.kirana.kirana_register.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(UserDao userDao,
                               PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/staff")
    public ResponseEntity<?> createStaff(
            @AuthenticationPrincipal UserPrincipal admin,
            @RequestBody CreateStaffRequest request
    ) {
        User staff = new User();
        staff.setName(request.getName());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setRole(Roles.STAFF);
        staff.setStatus(Status.ACTIVE);
        staff.setKiraanaId(admin.getUser().getKiraanaId());

        userDao.save(staff);

        return ResponseEntity.ok(Map.of("status", "STAFF_CREATED"));
    }
}
