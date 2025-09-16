package com.application.EmployeeReferralSystem.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applicants")
public class Applicant {

    @Id
    private String id;

    private String email;

    private String phoneNumber;

    private String resumeLink;

    @DBRef
    private User user;

    @DBRef
    private ResumeData resumeData;

    public Applicant() {}

    public Applicant(String id, String email, String phoneNumber, String resumeLink, User user, ResumeData resumeData) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.resumeLink = resumeLink;
        this.user = user;
        this.resumeData = resumeData;
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

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ResumeData getResumeData() {
        return resumeData;
    }

    public void setResumeData(ResumeData resumeData) {
        this.resumeData = resumeData;
    }
}
