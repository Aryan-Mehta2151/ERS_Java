package com.application.EmployeeReferralSystem.controllers;

import com.application.EmployeeReferralSystem.dtos.ApplicantDetailsDto;
import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;
import com.application.EmployeeReferralSystem.dtos.SkillRequestDTO;
import com.application.EmployeeReferralSystem.services.EmployeeService;
import com.application.EmployeeReferralSystem.services.JwtUtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;
    private final EmployeeService employeeService;

    public EmployeeController(AuthenticationManager authenticationManager, JwtUtilService jwtUtilService, EmployeeService employeeService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.employeeService = employeeService;
    }

    @GetMapping("/get-details-by-id/{id}")
    public ResponseEntity<EmployeeDetailsDto> getEmployeeDetailsById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        EmployeeDetailsDto employeeDetailsDto = employeeService.getEmployeeDetailsById(id);
        return employeeDetailsDto != null ? ResponseEntity.ok(employeeDetailsDto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/get-details-by-username/{username}")
    public ResponseEntity<EmployeeDetailsDto> getEmployeeDetailsByUsername(@RequestHeader("Authorization") String token, @PathVariable String username) {
        EmployeeDetailsDto employeeDetailsDto = employeeService.getEmployeeDetailsByUsername(username);
        return employeeDetailsDto != null ? ResponseEntity.ok(employeeDetailsDto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteEmployeeById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        return employeeService.deleteEmployeeById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ✅ New endpoint to search applicants based on skills
    @PostMapping("/search-applicants")
    public ResponseEntity<List<ApplicantDetailsDto>> searchApplicants(@RequestBody SkillRequestDTO request) {
        List<ApplicantDetailsDto> matchedApplicants = employeeService.searchApplicants(request.getSkills());
        return ResponseEntity.ok(matchedApplicants);
    }
}
