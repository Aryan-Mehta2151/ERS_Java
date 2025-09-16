package com.application.EmployeeReferralSystem.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HR")
public class HR {

    @Id
    private String id;

    private String email;

    private String phoneNumber;

    @DBRef
    private User user;

    @DBRef
    private Company company;

    public HR() {}

    public HR(String id, String email, String phoneNumber, User user, Company company) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
