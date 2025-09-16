package com.application.EmployeeReferralSystem.controllers;

import com.application.EmployeeReferralSystem.dtos.ReferralDto;
import com.application.EmployeeReferralSystem.services.IReferralService;
import com.application.EmployeeReferralSystem.services.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class ReferralController {

    private final IReferralService referralService;
    private final JwtUtilService jwtUtilService;

    @PostMapping("/refer/{applicantId}")
    public ResponseEntity<?> referApplicant(@PathVariable String applicantId,
                                            @RequestParam String jobId,
                                            @RequestBody String message,
                                            @RequestHeader("Authorization") String token) {

        String jwt = token.substring(7);
        String email = jwtUtilService.extractUsername(jwt); // Extracts email

        ReferralDto dto = new ReferralDto();
        dto.setApplicantId(applicantId);
        dto.setJobId(jobId);
        dto.setMessage(message);

        String result = referralService.referApplicant(dto, email);
        return ResponseEntity.ok(result);
    }
}
