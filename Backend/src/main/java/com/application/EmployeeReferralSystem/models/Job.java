package com.application.EmployeeReferralSystem.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "jobs")
public class Job {

    @Id
    private String jobId;

    @DBRef
    private Company company;

    @DBRef
    private HR postedBy;

    private int reqEmp;
    private List<String> skills;
    private String jobRole;
    private int minAts;

    public Job() {}

    public Job(Company company, HR postedBy, int reqEmp, List<String> skills, String jobRole, int minAts) {
        this.company = company;
        this.postedBy = postedBy;
        this.reqEmp = reqEmp;
        this.skills = skills;
        this.jobRole = jobRole;
        this.minAts = minAts;
    }

    public String getJobId() {
        return jobId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public HR getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(HR postedBy) {
        this.postedBy = postedBy;
    }

    public int getReqEmp() {
        return reqEmp;
    }

    public void setReqEmp(int reqEmp) {
        this.reqEmp = reqEmp;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public int getMinAts() {
        return minAts;
    }

    public void setMinAts(int minAts) {
        this.minAts = minAts;
    }
}
