package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.JobDTO;
import java.util.List;

public interface IJobService {
    JobDTO createJob(JobDTO jobDTO);
    JobDTO updateJob(String jobId, JobDTO jobDTO);
    void deleteJob(String jobId);
    List<JobDTO> getAllJobs();
    JobDTO getJobById(String jobId);
    List<JobDTO> getJobsForEmployee();

}
