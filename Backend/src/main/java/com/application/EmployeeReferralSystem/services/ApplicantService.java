package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.ApplicantDetailsDto;
import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;
import com.application.EmployeeReferralSystem.models.Applicant;
import com.application.EmployeeReferralSystem.models.Employee;
import com.application.EmployeeReferralSystem.models.User;
import com.application.EmployeeReferralSystem.repositories.ApplicantRepository;
import com.application.EmployeeReferralSystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicantService implements IApplicantService{

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;

    public ApplicantService(ApplicantRepository applicantRepository, UserRepository userRepository) {
        this.applicantRepository = applicantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApplicantDetailsDto getApplicantDetailsById(String id) {
        Optional<Applicant> applicantOptional = applicantRepository.findById(id);
        return applicantOptional.map(this::mapToDto).orElse(null);
    }

    @Override
    public ApplicantDetailsDto getApplicantDetailsByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            Optional<Applicant> applicantOptional = applicantRepository.findByUser(userOptional.get());
            if (applicantOptional.isPresent()) {
                return mapToDto(applicantOptional.get());
            }
        }
        return null;
    }

    @Override
    public boolean deleteApplicantById(String id) {
        Optional<Applicant> applicantOptional = applicantRepository.findById(id);
        if(applicantOptional.isPresent()){
            User user = applicantOptional.get().getUser();
            applicantRepository.delete(applicantOptional.get());

            if(user != null){
                userRepository.delete(user);
            }

            return true ;
        }
        return false;
    }

    private ApplicantDetailsDto mapToDto(Applicant applicant) {
        User user = applicant.getUser();
        return new ApplicantDetailsDto(
                applicant.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().toString(),
                user.getImageUrl(),
                applicant.getEmail(),
                applicant.getPhoneNumber(),
                applicant.getResumeLink()
        );
    }
}
