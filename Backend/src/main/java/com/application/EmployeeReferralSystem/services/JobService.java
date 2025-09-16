package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.JobDTO;
import com.application.EmployeeReferralSystem.models.Company;
import com.application.EmployeeReferralSystem.models.Employee;
import com.application.EmployeeReferralSystem.models.HR;
import com.application.EmployeeReferralSystem.models.Job;
import com.application.EmployeeReferralSystem.models.User;
import com.application.EmployeeReferralSystem.repositories.EmployeeRepository;
import com.application.EmployeeReferralSystem.repositories.HRRepository;
import com.application.EmployeeReferralSystem.repositories.JobRepository;
import com.application.EmployeeReferralSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService implements IJobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private HRRepository hrRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create a new job
    @Override
    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        HR hr = getLoggedInHR();
        Company company = hr.getCompany();

        if (company == null) {
            throw new RuntimeException("HR is not associated with any company.");
        }

        Job job = new Job(company, hr, jobDTO.getReqEmp(), jobDTO.getSkills(), jobDTO.getJobRole(), jobDTO.getMinAts());
        job = jobRepository.save(job);
        return mapToDTO(job);
    }

    // Get all jobs
    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get job by ID
    @Override
    public JobDTO getJobById(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found."));
        return mapToDTO(job);
    }

    // Update an existing job
    @Override
    @Transactional
    public JobDTO updateJob(String id, JobDTO jobDTO) {
        HR hr = getLoggedInHR();

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found."));

        if (!job.getPostedBy().getId().equals(hr.getId())) {
            throw new RuntimeException("Unauthorized: You can only update jobs posted by you.");
        }

        job.setReqEmp(jobDTO.getReqEmp());
        job.setSkills(jobDTO.getSkills());
        job.setJobRole(jobDTO.getJobRole());
        job.setMinAts(jobDTO.getMinAts());

        job = jobRepository.save(job);
        return mapToDTO(job);
    }

    // Delete a job by ID
    @Override
    @Transactional
    public void deleteJob(String id) {
        HR hr = getLoggedInHR();

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found."));

        if (!job.getPostedBy().getId().equals(hr.getId())) {
            throw new RuntimeException("Unauthorized: You can only delete jobs posted by you.");
        }

        jobRepository.delete(job);
    }

    // Get jobs for the logged-in employee’s company
    @Override
    public List<JobDTO> getJobsForEmployee() {
        User user = getLoggedInUser();

        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employee not found for logged-in user."));

        String companyName = employee.getCompanyName();

        if (companyName == null || companyName.isEmpty()) {
            throw new RuntimeException("Employee does not belong to any company.");
        }

        return jobRepository.findAll().stream()
                .filter(job -> job.getCompany() != null && companyName.equals(job.getCompany().getName()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper: Get the logged-in HR
    private HR getLoggedInHR() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for logged-in username: " + username));

        return hrRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("HR not found for logged-in user."));
    }

    // Helper: Get the logged-in User
    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for logged-in username: " + username));
    }

    // Helper: Convert Job entity to DTO
    private JobDTO mapToDTO(Job job) {
        return new JobDTO(
                job.getJobId(),
                job.getReqEmp(),
                job.getSkills(),
                job.getJobRole(),
                job.getMinAts()
        );
    }
}
