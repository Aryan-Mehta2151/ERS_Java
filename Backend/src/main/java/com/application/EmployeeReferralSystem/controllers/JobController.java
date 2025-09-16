package com.application.EmployeeReferralSystem.controllers;

import com.application.EmployeeReferralSystem.dtos.JobDTO;
import com.application.EmployeeReferralSystem.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {

    @Autowired
    private JobService jobService;

    // ✅ Explicitly mapped to /create and accessible only by HR
    @PostMapping("/create")
    @PreAuthorize("hasRole('HR')") // Restrict to HR role
    public JobDTO createJob(@RequestBody JobDTO jobDTO) {
        return jobService.createJob(jobDTO);
    }

    // ✅ Retrieve all jobs - can be accessed by HR
    @GetMapping
    @PreAuthorize("hasRole('HR')") // Restrict to HR role
    public List<JobDTO> getAllJobs() {
        return jobService.getAllJobs();
    }

    // ✅ Retrieve a specific job by ID - accessible only by HR
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('HR')") // Restrict to HR role
    public JobDTO getJobById(@PathVariable String id) {
        return jobService.getJobById(id);
    }

    // ✅ Update an existing job - accessible only by HR
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('HR')") // Restrict to HR role
    public JobDTO updateJob(@PathVariable String id, @RequestBody JobDTO jobDTO) {
        return jobService.updateJob(id, jobDTO);
    }

    // ✅ Delete a job by ID - accessible only by HR
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('HR')") // Restrict to HR role
    public void deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
    }

    // ✅ Get jobs for the logged-in employee’s company - Accessible only by Employees
    @GetMapping("/company-jobs")
    @PreAuthorize("hasRole('EMPLOYEE')") // Restrict to Employee role
    public List<JobDTO> getCompanyJobs() {
        return jobService.getJobsForEmployee();
    }
}
