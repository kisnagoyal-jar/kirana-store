package com.kirana.kirana_register.dto.mongodb;

import com.kirana.kirana_register.dao.mongodb.UserDao;

public class CustomerRequestDTO {

    private String name;
    private String phoneNumber;


    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
