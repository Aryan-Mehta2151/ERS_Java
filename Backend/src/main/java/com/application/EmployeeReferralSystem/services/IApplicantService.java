package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.ApplicantDetailsDto;
import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;

public interface IApplicantService {

    ApplicantDetailsDto getApplicantDetailsById(String id);

    ApplicantDetailsDto getApplicantDetailsByUsername(String username);

    boolean deleteApplicantById(String id);
}
