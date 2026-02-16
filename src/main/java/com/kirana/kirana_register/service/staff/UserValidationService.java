package com.kirana.kirana_register.service.staff;

import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.dto.mongodb.CustomerRequestDTO;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    private final UserDao userDao;

    public UserValidationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getOrCreateCustomer(CustomerRequestDTO customer, String kiraanaId) {

        return userDao
                .findByPhoneNumberAndKiraanaId(
                        customer.getPhoneNumber(),
                        kiraanaId
                )
                .map(this::validateExistingCustomer)
                .orElseGet(() -> createNewCustomer(customer, kiraanaId));
    }

    private User validateExistingCustomer(User user) {

        if (user.getRole() != Roles.CUSTOMER) {
            throw new IllegalStateException("User is not a CUSTOMER");
        }

        if (user.getStatus() == Status.BLOCKED) {
            throw new IllegalStateException("Customer is blocked by admin");
        }

        return user;
    }

    private User createNewCustomer(CustomerRequestDTO customer, String kiraanaId) {

        User newUser = new User();
        newUser.setName(customer.getName());
        newUser.setPhoneNumber(customer.getPhoneNumber());
        newUser.setKiraanaId(kiraanaId);

        // enforce business rules
        newUser.setRole(Roles.CUSTOMER);
        newUser.setStatus(Status.ACTIVE);
        newUser.setPassword(null); // IMPORTANT

        return userDao.save(newUser);
    }
}
