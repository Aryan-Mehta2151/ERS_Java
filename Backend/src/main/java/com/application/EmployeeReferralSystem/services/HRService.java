package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.dtos.HRDetailsDto;
import com.application.EmployeeReferralSystem.models.Company;
import com.application.EmployeeReferralSystem.models.HR;
import com.application.EmployeeReferralSystem.models.Job;
import com.application.EmployeeReferralSystem.models.User;
import com.application.EmployeeReferralSystem.repositories.CompanyRepository;
import com.application.EmployeeReferralSystem.repositories.HRRepository;
import com.application.EmployeeReferralSystem.repositories.JobRepository;
import com.application.EmployeeReferralSystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HRService implements IHRService {

    private final HRRepository hrRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;


    public HRService(HRRepository hrRepository, UserRepository userRepository, CompanyRepository companyRepository, JobRepository jobRepository) {
        this.hrRepository = hrRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public HRDetailsDto getHRDetailsById(String id) {
        Optional<HR> hrOptional = hrRepository.findById(id);
        return hrOptional.map(this::mapToDto).orElse(null);
    }

    @Override
    public HRDetailsDto getHRDetailsByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Optional<HR> hrOptional = hrRepository.findByUser(userOptional.get());
            return hrOptional.map(this::mapToDto).orElse(null);
        }
        return null;
    }

    @Override
    public boolean deleteHrById(String id) {
        Optional<HR> hrOptional = hrRepository.findById(id);
        if (hrOptional.isPresent()) {
            HR hr = hrOptional.get();
            User user = hr.getUser();
            hrRepository.delete(hr);

            if (user != null) {
                userRepository.delete(user);
            }

            return true;
        }
        return false;
    }

    @Override
    public List<Job> getJobsPostedByHR(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) return null;

        Optional<HR> hrOptional = hrRepository.findByUser(userOptional.get());
        if (hrOptional.isEmpty()) return null;

        String hrId = hrOptional.get().getId();
        return jobRepository.findByPostedBy_Id(hrId);
    }


    private HRDetailsDto mapToDto(HR hr) {
        User user = hr.getUser();
        Company company = hr.getCompany();

        return new HRDetailsDto(
                hr.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole().toString(),
                user.getImageUrl(),
                hr.getEmail(),
                hr.getPhoneNumber(),
                company != null ? company.getName() : null,  // Fetch company name safely
                company != null ? company.getCity() : null,
                company != null ? company.getEmail() : null,
                company != null ? company.getPincode() : null
        );
    }
}
