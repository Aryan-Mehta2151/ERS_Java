package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;
import com.application.EmployeeReferralSystem.dtos.HRDetailsDto;
import com.application.EmployeeReferralSystem.models.Job;

import java.util.List;

public interface IHRService {

    HRDetailsDto getHRDetailsById(String id);

    HRDetailsDto getHRDetailsByUsername(String username);

    boolean deleteHrById(String id);

    List<Job> getJobsPostedByHR(String username);

}
