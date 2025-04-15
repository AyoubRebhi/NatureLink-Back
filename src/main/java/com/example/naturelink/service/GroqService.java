package com.example.naturelink.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    private final WebClient webClient;

    public GroqService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.groq.com/openai/v1").build();
    }

    public String generateActivityWithPrompt(Map<String, String> params) {
        String prompt = buildPromptFromParams(params);

        // Prepare proper JSON structure using Map
        Map<String, Object> requestBody = Map.of(
                "model", "llama3-70b-8192",
                "temperature", 0.8,
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a helpful assistant that generates JSON activity data."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + groqApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String buildPromptFromParams(Map<String, String> params) {
        return """
            Generate a JSON object for an outdoor activity located in Tunisia.
            The activity should be realistic and based on the following preferences:

            - location: %s
            - type: %s
            - difficulty: %s
            - mood: %s
            - tags: %s

            Required fields:
            - name
            - description
            - location
            - duration (in hours)
            - price (in TND)
            - maxParticipants
            - difficultyLevel (Easy, Moderate, Hard)
            - requiredEquipment (as a list of strings)
            - type
            - mood (list of moods)
            - tags (list of tags)

            Respond ONLY with the JSON object.
            """.formatted(
                params.getOrDefault("location", "anywhere in Tunisia"),
                params.getOrDefault("type", "any type"),
                params.getOrDefault("difficulty", "any"),
                params.getOrDefault("mood", "any"),
                params.getOrDefault("tags", "any")
        );
    }
}
