package com.application.EmployeeReferralSystem.dtos;

public class EmployeeDetailsDto {
    private String id;
    private String username;
    private String password;
    private String role;
    private String imageUrl;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String designation;

    public EmployeeDetailsDto(String id, String username, String password, String role, String imageUrl, String email, String phoneNumber, String companyName, String designation) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.designation = designation;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
