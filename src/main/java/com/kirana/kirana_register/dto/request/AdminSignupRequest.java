package com.kirana.kirana_register.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignupRequest {

    private String adminName;
    private String adminPhone;
    private String password;

    private String kiranaName;
    private String kiranaAddress;
}
