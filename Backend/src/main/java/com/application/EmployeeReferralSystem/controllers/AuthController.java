package com.application.EmployeeReferralSystem.controllers;

import com.application.EmployeeReferralSystem.dtos.LoginRequestDto;
import com.application.EmployeeReferralSystem.dtos.LoginResponseDto;
import com.application.EmployeeReferralSystem.dtos.RegisterRequestDto;
import com.application.EmployeeReferralSystem.services.AuthService;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> LoginUser(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.LoginUser(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<String> RegisterUser(
            @RequestPart(value = "username", required = false) String username,
            @RequestPart(value = "password", required = false) String password,
            @RequestPart(value = "email", required = false) String email,
            @RequestPart(value = "phoneNumber", required = false) String phoneNumber,
            @RequestPart(value = "role", required = false) String role,
            @RequestPart(value = "companyName", required = false) String companyName,
            @RequestPart(value = "companyCity", required = false) String companyCity,
            @RequestPart(value = "companyEmail", required = false) String companyEmail,
            @RequestPart(value = "companyPincode", required = false) String companyPincode,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "imageUrl", required = false) String imageUrl,
            @RequestPart(value = "resumeLink", required = false) String resumeLink,
            @RequestPart(value = "department", required = false) String department,
            @RequestPart(value = "designation", required = false) String designation
    ) throws TikaException, IOException {

        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername(username);
        registerRequestDto.setPassword(password);
        registerRequestDto.setEmail(email);
        registerRequestDto.setPhoneNumber(phoneNumber);
        registerRequestDto.setRole(role);
        registerRequestDto.setCompanyName(companyName);
        registerRequestDto.setCompanyCity(companyCity);
        registerRequestDto.setCompanyEmail(companyEmail);
        registerRequestDto.setCompanyPincode(companyPincode);
        registerRequestDto.setResume(resume);
        registerRequestDto.setImageUrl(imageUrl);
        registerRequestDto.setResumeLink(resumeLink);
        registerRequestDto.setDepartment(department);
        registerRequestDto.setDesignation(designation);

        String response = authService.RegisterUser(registerRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
