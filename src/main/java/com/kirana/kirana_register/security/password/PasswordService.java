package com.kirana.kirana_register.security.password;

public interface PasswordService {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);

}
