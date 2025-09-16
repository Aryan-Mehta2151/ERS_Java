package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.ReferralDto;
import com.application.EmployeeReferralSystem.models.*;
import com.application.EmployeeReferralSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReferralServiceImpl implements IReferralService {

    private final ReferralRepository referralRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicantRepository applicantRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public String referApplicant(ReferralDto dto, String employeeusername) {
        // Fetch user by email
        User user = userRepository.findByUsername(employeeusername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the employee by user reference
        Employee employee = employeeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Check if referral already exists
        boolean alreadyReferred = referralRepository.existsByApplicant_IdAndJob_JobIdAndReferredBy_Id(
                dto.getApplicantId(), dto.getJobId(), employee.getId());

        if (alreadyReferred) {
            return "You have already referred this applicant for this job.";
        }

        // Get referenced entities
        Applicant applicant = applicantRepository.findById(dto.getApplicantId())
                .orElseThrow(() -> new RuntimeException("Applicant not found"));

        Job job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Referral referral = new Referral();
        referral.setReferredBy(employee);
        referral.setApplicant(applicant);
        referral.setJob(job);
        referral.setMessage(dto.getMessage());

        referralRepository.save(referral);

        return "Referral submitted successfully.";
    }
}
