package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.ReferralDto;

public interface IReferralService {
    String referApplicant(ReferralDto referralDto, String employeeEmail);
}
