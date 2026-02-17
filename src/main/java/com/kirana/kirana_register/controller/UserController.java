package com.kirana.kirana_register.controller;

import com.kirana.kirana_register.dto.request.UserRequestDTO;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.service.UserService;
import com.kirana.kirana_register.service.validationServices.UserValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserValidationService userValidationService;

    public UserController(UserService userService, UserValidationService userValidationService) {
        this.userService = userService;
        this.userValidationService = userValidationService;
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','SUPER_ADMIN')")
    public ResponseEntity<User> createUser(UserRequestDTO request) {
        userValidationService.authorizeUser(request);
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','SUPER_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable String id, UserRequestDTO request) {
        userValidationService.authorizeUser(request);
        return ResponseEntity.ok(userService.updateUser(id,request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF','SUPER_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

}
