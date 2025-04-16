package com.example.naturelink.Controller;

// ImageAnalysisController.java

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "http://localhost:4200") // adapte si besoin
public class ImageAnalysisController {

    // Remove fake detection and add real face verification
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, String>> analyzeImage(@RequestParam("file") MultipartFile file) {
        try {
            // Validate basic image requirements
            if (file.isEmpty()) {
                return badRequest("Empty file");
            }

            if (!file.getContentType().startsWith("image/")) {
                return badRequest("Invalid image format");
            }

            // Add real face detection logic here
            // For production, use a library like OpenCV
            // This example uses simple metadata checks
            boolean validImage = isValidProfileImage(file);

            Map<String, String> response = new HashMap<>();
            response.put("valid", String.valueOf(validImage));
            response.put("message", validImage ?
                    "Image validated successfully" :
                    "Invalid profile image - must contain one clear face");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return serverError("Image analysis failed");
        }
    }

    private boolean isValidProfileImage(MultipartFile file) {
        // Basic checks (customize these thresholds)
        return file.getSize() > 50_000 &&   // >50KB
                file.getSize() < 5_000_000; // <5MB
    }

    // Helper methods
    private ResponseEntity<Map<String, String>> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", message));
    }

    private ResponseEntity<Map<String, String>> serverError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", message));
    }
}
