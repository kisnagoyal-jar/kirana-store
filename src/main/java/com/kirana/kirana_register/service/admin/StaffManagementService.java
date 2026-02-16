package com.kirana.kirana_register.service.admin;

import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import com.kirana.kirana_register.repository.mongodb.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StaffManagementService {

    private final UserRepository userRepository;

    public StaffManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createStaff(String kiraanaId, String name,
                            String phone, String password) {

        if (userRepository.existsByPhoneNumber(phone)) {
            throw new RuntimeException("Staff already exists");
        }

        User staff = new User();
        staff.setName(name);
        staff.setPhoneNumber(phone);
        staff.setPassword(password);
        staff.setRole(Roles.STAFF);
        staff.setStatus(Status.ACTIVE);
        staff.setKiraanaId(kiraanaId);

        return userRepository.save(staff);
    }

    public void blockStaff(String staffId) {
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setStatus(Status.BLOCKED);
        userRepository.save(staff);
    }
}

