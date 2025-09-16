package com.application.EmployeeReferralSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferralDto {

    private String employeeId;
    private String applicantId;
    private String jobId;
    private String referralDate; // Optional

    private String message; // ✅ Message to HR
}
