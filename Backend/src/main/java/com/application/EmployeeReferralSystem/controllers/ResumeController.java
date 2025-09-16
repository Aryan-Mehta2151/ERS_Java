package com.application.EmployeeReferralSystem.controllers;

import org.apache.tika.exception.TikaException;
import com.application.EmployeeReferralSystem.models.ResumeData;
import com.application.EmployeeReferralSystem.services.ResumeService;
import com.application.EmployeeReferralSystem.Util.ResumeParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {
    private final ResumeParser resumeParser;
    private final ResumeService resumeService;

    public ResumeController(ResumeParser resumeParser, ResumeService resumeService) {
        this.resumeParser = resumeParser;
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResumeData> uploadResume(@RequestParam("file") MultipartFile file) throws IOException, TikaException {
        // Extract text from PDF
        String extractedText = resumeParser.extractText(file);

        // Process resume with Gemini API
        ResumeData resumeData = resumeService.processResume(extractedText);

        if (resumeData == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Save extracted resume data to the database
        ResumeData savedResume = resumeService.saveResumeData(resumeData);

        return ResponseEntity.ok(savedResume);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeData> getResumeById(@PathVariable String id) {
        ResumeData resumeData = resumeService.getResumeById(id);
        return resumeData != null ? ResponseEntity.ok(resumeData) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResumeById(@PathVariable String id) {
        boolean deleted = resumeService.deleteResumeById(id);
        return deleted ? ResponseEntity.ok("Resume deleted successfully.") : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResumeData>> getAllResumes() {
        List<ResumeData> resumes = resumeService.getAllResumes();
        return ResponseEntity.ok(resumes);
    }
}
