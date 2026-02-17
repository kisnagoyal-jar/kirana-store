package com.kirana.kirana_register.service;

import com.kirana.kirana_register.dto.request.UserRequestDTO;
import com.kirana.kirana_register.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public UserPrincipal getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (UserPrincipal) authentication.getPrincipal();
    }

    public String getCurrentRole() {
        return getCurrentUser().getRole();
    }

    public String getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

}
