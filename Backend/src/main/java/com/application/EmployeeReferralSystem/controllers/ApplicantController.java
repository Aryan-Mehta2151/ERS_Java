package com.application.EmployeeReferralSystem.controllers;

import com.application.EmployeeReferralSystem.dtos.ApplicantDetailsDto;
import com.application.EmployeeReferralSystem.models.ResumeData;
import com.application.EmployeeReferralSystem.services.ApplicantService;
import com.application.EmployeeReferralSystem.services.JwtUtilService;
import com.application.EmployeeReferralSystem.services.ResumeService;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.application.EmployeeReferralSystem.Util.ResumeParser;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;
    private final ApplicantService applicantService;
    private final ResumeParser resumeParser;
    private final ResumeService resumeService;

    public ApplicantController(AuthenticationManager authenticationManager, JwtUtilService jwtUtilService, ApplicantService applicantService, ResumeParser resumeParser, ResumeService resumeService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.applicantService = applicantService;
        this.resumeParser = resumeParser;
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeData> uploadResume(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) throws IOException, TikaException {
        // Extract text directly from uploaded file
        String extractedText = resumeParser.extractText(file);

        // Get skills from Gemini API
        ResumeData resumeData = resumeService.processResume(extractedText);

        return ResponseEntity.ok(resumeData);
    }

    @GetMapping("/get-details-by-id/{id}")
    public ResponseEntity<ApplicantDetailsDto> getApplicantDetailsById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        ApplicantDetailsDto applicantDetailsDto = applicantService.getApplicantDetailsById(id);
        return applicantDetailsDto != null ? new ResponseEntity<>(applicantDetailsDto, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-details-by-username/{username}")
    public ResponseEntity<ApplicantDetailsDto> getApplicantDetailsByUsername(@RequestHeader("Authorization") String token, @PathVariable String username) {
        ApplicantDetailsDto applicantDetailsDto = applicantService.getApplicantDetailsByUsername(username);
        return applicantDetailsDto != null ? new ResponseEntity<>(applicantDetailsDto, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteApplicantById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        return applicantService.deleteApplicantById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
