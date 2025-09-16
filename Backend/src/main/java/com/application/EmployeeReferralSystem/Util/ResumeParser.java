package com.application.EmployeeReferralSystem.Util;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class ResumeParser {
    private final Tika tika = new Tika();

    // Extract text from MultipartFile
    public String extractText(MultipartFile file) throws IOException, TikaException {
        return tika.parseToString(file.getInputStream());
    }

    // Extract text from File
    public String extractText(File file) throws IOException, TikaException {
        return tika.parseToString(Files.newInputStream(file.toPath()));
    }
}
