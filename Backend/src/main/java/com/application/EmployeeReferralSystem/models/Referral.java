package com.application.EmployeeReferralSystem.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "referrals")
public class Referral {

    @Id
    private String id;

    @DBRef
    private Employee referredBy;

    @DBRef
    private Applicant applicant;

    @DBRef
    private Job job;

    private String message; // HR message from the employee

    public Referral() {}

    public Referral(String id, Employee referredBy, Applicant applicant, Job job, String message) {
        this.id = id;
        this.referredBy = referredBy;
        this.applicant = applicant;
        this.job = job;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(Employee referredBy) {
        this.referredBy = referredBy;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
