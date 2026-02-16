package com.kirana.kirana_register.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CreateStaffRequest {

    private String name;
    private String phoneNumber;
    private String password;

}
