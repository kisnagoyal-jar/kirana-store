package com.kirana.kirana_register.service.validationServices;

import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.dto.request.UserRequestDTO;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.service.CurrentUserService;
import com.kirana.kirana_register.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    private final UserDao userDao;
    private final UserService userService;
    private final CurrentUserService currentUserService;

    public UserValidationService(UserDao userDao, UserService userService, CurrentUserService currentUserService) {
        this.userDao = userDao;
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    public User getCustomer(UserRequestDTO customer) {

        return userDao
                .findByPhoneNumberAndKiranaId(
                        customer.getPhoneNumber(),
                        customer.getKiranaId()
                )
                .map(this::validateExistingCustomer)
                .orElseGet(() -> userService.createUser(customer));
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

    public void authorizeUser(UserRequestDTO userRequestDTO) {
        UserPrincipal currentLoggedInUser = currentUserService.getCurrentUser();

        if (currentLoggedInUser.getRole().equals("SUPER_ADMIN")) {
            if (userRequestDTO.getRole().equals("SUPER_ADMIN")) {
                throw new RuntimeException("SUPER_ADMIN cannot create another SUPER_ADMIN");
            }
            if(!currentLoggedInUser.getKiranaId().equals(userRequestDTO.getKiranaId())){
                throw new RuntimeException("SUPER_ADMIN can only create users for their own Kirana");
            }
        }

        if (currentLoggedInUser.getRole().equals("ADMIN")) {
            if (userRequestDTO.getRole().equals("SUPER_ADMIN") || userRequestDTO.getRole().equals("ADMIN") ) {
                throw new RuntimeException("ADMIN cannot create SUPER_ADMIN or ADMIN");
            }

            if(!currentLoggedInUser.getKiranaId().equals(userRequestDTO.getKiranaId())){
                throw new RuntimeException("ADMIN can only create users for their own Kirana");
            }
        }

        if(currentLoggedInUser.getRole().equals("STAFF")) {
            if(userRequestDTO.getRole().equals("SUPER_ADMIN") || userRequestDTO.getRole().equals("ADMIN") || userRequestDTO.getRole().equals("STAFF")) {
                throw new RuntimeException("STAFF cannot create SUPER_ADMIN, ADMIN or STAFF");
            }
            if(!currentLoggedInUser.getKiranaId().equals(userRequestDTO.getKiranaId())){
                throw new RuntimeException("STAFF can only create users for their own Kirana");
            }


        }
    }

}
