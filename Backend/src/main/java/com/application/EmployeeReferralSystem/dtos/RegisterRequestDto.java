package com.application.EmployeeReferralSystem.dtos;

import org.springframework.web.multipart.MultipartFile;

public class RegisterRequestDto {

    // User (For All)
    private String username;
    private String password;
    private String email;
    private String role;
    private String imageUrl;
    private String phoneNumber;

    // Applicant
    private String resumeLink;
    private MultipartFile resume;

    // Employee
    private String designation;
    private String department;

    // HR
    private String companyName;
    private String companyCity;
    private String companyEmail;
    private String companyPincode;

    public RegisterRequestDto() {}

    public RegisterRequestDto(String username, String password, String email, String role, String imageUrl,
                              String phoneNumber, String resumeLink, MultipartFile resume, String designation,
                              String department, String companyName, String companyCity, String companyEmail,
                              String companyPincode) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.resumeLink = resumeLink;
        this.resume = resume;
        this.designation = designation;
        this.department = department;
        this.companyName = companyName;
        this.companyCity = companyCity;
        this.companyEmail = companyEmail;
        this.companyPincode = companyPincode;
    }

    // Getters and Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public MultipartFile getResume() {
        return resume;
    }

    public void setResume(MultipartFile resume) {
        this.resume = resume;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPincode() {
        return companyPincode;
    }

    public void setCompanyPincode(String companyPincode) {
        this.companyPincode = companyPincode;
    }
}
