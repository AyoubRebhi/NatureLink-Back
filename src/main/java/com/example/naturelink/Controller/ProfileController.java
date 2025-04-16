package com.example.naturelink.Controller;

import com.example.naturelink.Service.NLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// ProfileController.java
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private NLPService nlpService;

    @PostMapping("/suggestions")
    public ResponseEntity<?> getSuggestions(@RequestBody Map<String, String> request) {
        try {
            String bioText = request.get("text");
            if (bioText == null || bioText.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Text input is required");
            }
            return ResponseEntity.ok(nlpService.analyzeBio(bioText));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not process request");
        }
    }
}