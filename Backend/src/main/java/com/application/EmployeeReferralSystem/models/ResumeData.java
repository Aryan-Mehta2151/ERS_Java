package com.application.EmployeeReferralSystem.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "resume_details")
public class ResumeData {

    @Id
    private String id;

    private List<String> skills;
    private int experience;  // Number of years of experience
    private String degree;
    private int atsScore;  // ATS score for resume matching

    public ResumeData() {}

    public ResumeData(List<String> skills, int experience, String degree, int atsScore) {
        this.skills = skills;
        this.experience = experience;
        this.degree = degree;
        this.atsScore = atsScore;
    }

    public ResumeData(List<String> skills) {
    }

    public String getId() {
        return id;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }
}
