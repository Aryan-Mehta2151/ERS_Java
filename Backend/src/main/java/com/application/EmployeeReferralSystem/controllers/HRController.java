package com.application.EmployeeReferralSystem.controllers;

import com.application.EmployeeReferralSystem.dtos.EmployeeDetailsDto;
import com.application.EmployeeReferralSystem.dtos.HRDetailsDto;
import com.application.EmployeeReferralSystem.models.Role;
import com.application.EmployeeReferralSystem.services.HRService;
import com.application.EmployeeReferralSystem.services.JwtUtilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hr")
public class HRController {
    private final AuthenticationManager authenticationManager;

    private final JwtUtilService jwtUtilService;

    private final HRService hrService;

    public HRController(AuthenticationManager authenticationManager, JwtUtilService jwtUtilService, HRService hrService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
        this.hrService = hrService;
    }

    @GetMapping("/get-details-by-id/{id}")
    public ResponseEntity<HRDetailsDto> GetApplicantDetailsById(@RequestHeader("Authorization") String token, @PathVariable String id){
        HRDetailsDto hrDetailsDto = hrService.getHRDetailsById(id);

        if(hrDetailsDto == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hrDetailsDto, HttpStatus.OK);
    }

    @GetMapping("/my-posted-jobs")
    public ResponseEntity<?> getMyPostedJobs(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        String username = jwtUtilService.extractUsername(jwtToken);

        var jobs = hrService.getJobsPostedByHR(username);

        if (jobs == null || jobs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No jobs found for this HR");
        }

        return ResponseEntity.ok(jobs);
    }


    @GetMapping("/get-details-by-username/{username}")
    public ResponseEntity<HRDetailsDto> GetApplicantDetailsByUsername(@RequestHeader("Authorization") String token, @PathVariable String username){
        HRDetailsDto hrDetailsDto = hrService.getHRDetailsByUsername(username);

        if(hrDetailsDto == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(hrDetailsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> DeleteHrById(@RequestHeader("Authorization") String token, @PathVariable String id){
        boolean isDelete = hrService.deleteHrById(id);

        if(!isDelete){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
