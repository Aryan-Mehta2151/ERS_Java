package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.Util.ResumeParser;
import com.application.EmployeeReferralSystem.dtos.LoginResponseDto;
import com.application.EmployeeReferralSystem.dtos.RegisterRequestDto;
import com.application.EmployeeReferralSystem.models.*;
import com.application.EmployeeReferralSystem.repositories.*;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;
    private final EmployeeRepository employeeRepository;
    private final HRRepository hrRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilService jwtUtilService;
    private final ResumeRepository resumeRepository;
    private final ResumeParser resumeParser;
    private final ResumeService resumeService;

    public AuthService(UserRepository userRepository, ApplicantRepository applicantRepository,
                       EmployeeRepository employeeRepository, HRRepository hrRepository,
                       CompanyRepository companyRepository, PasswordEncoder passwordEncoder,
                       JwtUtilService jwtUtilService, ResumeRepository resumeRepository,
                       ResumeParser resumeParser, ResumeService resumeService) {
        this.userRepository = userRepository;
        this.applicantRepository = applicantRepository;
        this.employeeRepository = employeeRepository;
        this.hrRepository = hrRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilService = jwtUtilService;
        this.resumeRepository = resumeRepository;
        this.resumeParser = resumeParser;
        this.resumeService = resumeService;
    }

    @Override
    public LoginResponseDto LoginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        String accessToken = jwtUtilService.generateAccessToken(username, user.getRole().name());
        String refreshToken = jwtUtilService.generateRefreshToken(username, user.getRole().name());

        LoginResponseDto response = new LoginResponseDto();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Transactional
    @Override
    public String RegisterUser(RegisterRequestDto registerRequestDto) throws TikaException, IOException {
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists with that username");
        }

        // Create User
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setImageUrl(registerRequestDto.getImageUrl());
        Role userRole = Role.valueOf(registerRequestDto.getRole().toUpperCase());
        user.setRole(userRole);

        // Resume-related variables
        ResumeData resumeData = new ResumeData();
        String resumeLink = null;

        if (userRole == Role.APPLICANT && registerRequestDto.getResume() != null && !registerRequestDto.getResume().isEmpty()) {
            MultipartFile resume = registerRequestDto.getResume();

            // Save file and generate link
            resumeLink = saveResumeFile(resume);

            // Parse and store resumeData
            String resumeText = resumeParser.extractText(resume);
            resumeData = resumeService.processResume(resumeText);

            if (resumeData != null && !resumeData.getSkills().isEmpty()) {
                resumeRepository.save(resumeData);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to process resume");
            }
        }

        switch (userRole) {
            case APPLICANT:
                Applicant applicant = new Applicant();
                applicant.setEmail(registerRequestDto.getEmail());
                applicant.setPhoneNumber(registerRequestDto.getPhoneNumber());
                applicant.setResumeLink(resumeLink); // ✅ Set generated resume URL
                applicant.setUser(user);
                applicant.setResumeData(resumeData);

                userRepository.save(user);
                applicantRepository.save(applicant);
                break;

            case EMPLOYEE:
                Optional<Company> existingCompany = companyRepository.findByName(registerRequestDto.getCompanyName());

                if (existingCompany.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Company does not exist: " + registerRequestDto.getCompanyName());
                }

                Employee employee = new Employee();
                employee.setEmail(registerRequestDto.getEmail());
                employee.setPhoneNumber(registerRequestDto.getPhoneNumber());
                employee.setDepartment(registerRequestDto.getDepartment());
                employee.setDesignation(registerRequestDto.getDesignation());
                employee.setCompanyName(registerRequestDto.getCompanyName());
                employee.setImageURL(registerRequestDto.getImageUrl());
                employee.setUser(user);

                userRepository.save(user);
                employeeRepository.save(employee);
                break;

            case HR:
                Company company = getOrCreateCompany(registerRequestDto);
                HR hr = new HR();
                hr.setEmail(registerRequestDto.getEmail());
                hr.setPhoneNumber(registerRequestDto.getPhoneNumber());
                hr.setCompany(company);
                hr.setUser(user);

                userRepository.save(user);
                hrRepository.save(hr);
                break;
        }

        return "User Registered Successfully";
    }

    // ✅ Helper to save resume and return public link
    private String saveResumeFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/resumes/";
        String originalFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // avoid filename collisions
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        File dest = new File(uploadDir + originalFilename);
        file.transferTo(dest);

        // Return viewable URL
        return "http://localhost:8197/resumes/" + originalFilename;
    }

    private Company getOrCreateCompany(RegisterRequestDto registerRequestDto) {
        return companyRepository.findByName(registerRequestDto.getCompanyName())
                .orElseGet(() -> {
                    Company newCompany = new Company();
                    newCompany.setCity(registerRequestDto.getCompanyCity());
                    newCompany.setEmail(registerRequestDto.getCompanyEmail());
                    newCompany.setName(registerRequestDto.getCompanyName());
                    newCompany.setPincode(registerRequestDto.getCompanyPincode());
                    return companyRepository.save(newCompany);
                });
    }
}
