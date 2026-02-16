package com.kirana.kirana_register.dto.mongodb;

import com.kirana.kirana_register.dao.mongodb.UserDao;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

    private String name;
    private String phoneNumber;
}
