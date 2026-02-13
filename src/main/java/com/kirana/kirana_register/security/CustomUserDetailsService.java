package com.kirana.kirana_register.security;

import com.kirana.kirana_register.dao.mongodb.UserDao;
import com.kirana.kirana_register.entity.mongodb.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Spring Security calls this method during authentication.
     *
     * @param phoneNumber the login identifier (username in Spring terms)
     */
    @Override
    public UserDetails loadUserByUsername(String phoneNumber)
            throws UsernameNotFoundException {

        User user = userDao.findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with phone number: " + phoneNumber
                        )
                );

        return new UserPrincipal(user);
    }
}
