package com.application.EmployeeReferralSystem.dtos;

public class HRDetailsDto {
    private String id;
    private String username;
    private String password;
    private String role;
    private String imageUrl;
    private String email;
    private String phoneNumber;
    private String companyName;

    public HRDetailsDto(String id, String username, String password, String role, String imageUrl, String email, String phoneNumber,String companyName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
    }

    public HRDetailsDto(String id, String username, String password, String string, String imageUrl, String email, String phoneNumber, String companyName, String s, String s1, String s2) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
