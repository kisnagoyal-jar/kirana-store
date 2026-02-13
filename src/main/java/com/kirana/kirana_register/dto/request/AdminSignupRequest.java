package com.kirana.kirana_register.dto.request;

public class AdminSignupRequest {

    private String adminName;
    private String adminPhone;
    private String password;

    private String kiranaName;
    private String kiranaAddress;

    public String getAdminName() { return adminName; }
    public String getAdminPhone() { return adminPhone; }
    public String getPassword() { return password; }
    public String getKiranaName() { return kiranaName; }
    public String getKiranaAddress() { return kiranaAddress; }

    public void setAdminName(String adminName) { this.adminName = adminName; }
    public void setAdminPhone(String adminPhone) { this.adminPhone = adminPhone; }
    public void setPassword(String password) { this.password = password; }
    public void setKiranaName(String kiranaName) { this.kiranaName = kiranaName; }
    public void setKiranaAddress(String kiranaAddress) { this.kiranaAddress = kiranaAddress; }
}
