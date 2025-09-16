package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.ApplicantDetailsDto;
import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;
import com.application.EmployeeReferralSystem.models.Applicant;
import com.application.EmployeeReferralSystem.models.Employee;
import com.application.EmployeeReferralSystem.models.ResumeData;
import com.application.EmployeeReferralSystem.models.User;
import com.application.EmployeeReferralSystem.repositories.ApplicantRepository;
import com.application.EmployeeReferralSystem.repositories.EmployeeRepository;
import com.application.EmployeeReferralSystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ApplicantRepository applicantRepository;

    public EmployeeService(UserRepository userRepository, EmployeeRepository employeeRepository, ApplicantRepository applicantRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.applicantRepository = applicantRepository;
    }

    @Override
    public EmployeeDetailsDto getEmployeeDetailsById(String id) {
        return employeeRepository.findById(id).map(this::mapToDto).orElse(null);
    }

    @Override
    public EmployeeDetailsDto getEmployeeDetailsByUsername(String username) {
        return userRepository.findByUsername(username)
                .flatMap(user -> employeeRepository.findByUser(user))
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public boolean deleteEmployeeById(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            User user = employeeOptional.get().getUser();
            employeeRepository.delete(employeeOptional.get());
            if (user != null) {
                userRepository.delete(user);
            }
            return true;
        }
        return false;
    }

    // ✅ Search applicants whose ResumeData contains ALL requested skills
    public List<ApplicantDetailsDto> searchApplicants(List<String> requiredSkills) {
        return applicantRepository.findAll().stream()
                .filter(applicant -> {
                    ResumeData resumeData = applicant.getResumeData();
                    if (resumeData == null || resumeData.getSkills() == null) return false;
                    return resumeData.getSkills().containsAll(requiredSkills);  // ✅ Must contain all requested skills
                })
                .map(this::mapApplicantToDto)
                .collect(Collectors.toList());
    }

    private EmployeeDetailsDto mapToDto(Employee employee) {
        User user = employee.getUser();
        return new EmployeeDetailsDto(
                employee.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().toString(),
                user.getImageUrl(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getCompanyName(),
                employee.getDesignation()
        );
    }

    private ApplicantDetailsDto mapApplicantToDto(Applicant applicant) {
        return new ApplicantDetailsDto(
                applicant.getId(),
                applicant.getUser().getUsername(),
                applicant.getUser().getPassword(),
                applicant.getUser().getRole().toString(),
                applicant.getUser().getImageUrl(),
                applicant.getEmail(),
                applicant.getPhoneNumber(),
                applicant.getResumeLink()
        );
    }
}
