package com.kirana.kirana_register.dto.request;


import com.kirana.kirana_register.enums.Roles;
import com.kirana.kirana_register.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserRequestDTO {

    private String name;
    private String phoneNumber;
    private String password;
    private String kiranaId;
    private Roles role;
    private Status status;

}
