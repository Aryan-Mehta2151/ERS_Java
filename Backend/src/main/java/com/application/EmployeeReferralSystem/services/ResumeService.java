package com.application.EmployeeReferralSystem.services;

import com.application.EmployeeReferralSystem.configuration.GeminiConfig;
import com.application.EmployeeReferralSystem.models.ResumeData;
import com.application.EmployeeReferralSystem.repositories.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class  ResumeService {
    private static final Logger logger = LoggerFactory.getLogger(ResumeService.class);
    private final WebClient webClient;
    private final GeminiConfig geminiConfig;
    private final ResumeRepository resumeRepository;
    private final ObjectMapper objectMapper;

    public ResumeService(WebClient.Builder webClientBuilder, GeminiConfig geminiConfig, ResumeRepository resumeRepository, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com")
                .defaultHeader("Content-Type", "application/json")
                .build();
        this.geminiConfig = geminiConfig;
        this.resumeRepository = resumeRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void checkApiKey() {
        if (geminiConfig.getKey() == null || geminiConfig.getKey().isEmpty()) {
            logger.error("🚨 GEMINI API KEY IS NOT SET! Check your application.properties.");
        } else {
            logger.info("✅ Gemini API Key successfully read.");
        }
    }

    public ResumeData processResume(String resumeText) {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiConfig.getKey();

        String prompt = """
                Extract the following details from the resume:
                - Skills (list)
                - Total years of experience (int)
                - Highest degree (string)
                - ATS score (out of 100) (int)

                Return the response in JSON format with keys: skills, experience, degree, atsScore.
                
                Resume text:
                """ + resumeText;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        try {
            Map<String, Object> response = webClient.post()
                    .uri(apiUrl)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return extractResumeDetails(response);
        } catch (Exception e) {
            logger.error("❌ Error calling Gemini API: {}", e.getMessage());
            return null;
        }
    }

    private ResumeData extractResumeDetails(Map<String, Object> response) {
        if (response == null || !response.containsKey("candidates")) {
            logger.error("❌ Invalid response from Gemini API.");
            return null;
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates.isEmpty() || !candidates.get(0).containsKey("content")) {
            logger.error("❌ No valid candidate response.");
            return null;
        }

        String jsonResponse = (String) ((List<Map<String, String>>) ((Map<String, Object>) candidates.get(0).get("content")).get("parts")).get(0).get("text");

        // ✅ Clean JSON response
        jsonResponse = jsonResponse.replaceAll("```json", "").replaceAll("```", "").trim();

        logger.info("✅ Extracted Resume Details: {}", jsonResponse);

        try {
            return objectMapper.readValue(jsonResponse, ResumeData.class);
        } catch (Exception e) {
            logger.error("❌ Error parsing JSON response: {}", e.getMessage());
            return null;
        }
    }

    public ResumeData saveResumeData(ResumeData resumeData) {
        return resumeRepository.save(resumeData);
    }

    public ResumeData getResumeById(String id) {
        Optional<ResumeData> resumeData = resumeRepository.findById(id);
        return resumeData.orElse(null);
    }

    public boolean deleteResumeById(String id) {
        if (resumeRepository.existsById(id)) {
            resumeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ✅ Get all resumes method added
    public List<ResumeData> getAllResumes() {
        List<ResumeData> resumes = resumeRepository.findAll();
        if (resumes.isEmpty()) {
            logger.warn("⚠️ No resumes found in the database.");
        } else {
            logger.info("✅ Retrieved {} resumes from the database.", resumes.size());
        }
        return resumes;
    }
}
