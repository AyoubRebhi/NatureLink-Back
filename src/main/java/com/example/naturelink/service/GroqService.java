package com.example.naturelink.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.*;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${unsplash.access.key}")
    private String unsplashAccessKey;

    private final WebClient webClient;
    private final WebClient unsplashWebClient;

    public GroqService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.groq.com/openai/v1").build();
        this.unsplashWebClient = webClientBuilder.baseUrl("https://api.unsplash.com").build();

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

    // Add to your GroqService.java
    public List<String> getActivityImages(String description) {
        // Step 1: Generate a search query using Groq
        String searchQuery = generateImageSearchQuery(description);

        // Step 2: Fetch images from Unsplash
        return fetchUnsplashImages(searchQuery);
    }

    private String generateImageSearchQuery(String description) {
        Map<String, Object> requestBody = Map.of(
                "model", "llama3-70b-8192",
                "temperature", 0.7,
                "messages", List.of(
                        Map.of("role", "system", "content", "Generate a ONE-LINE Google image search query for: " + description),
                        Map.of("role", "user", "content", "Return ONLY the raw search query text with no additional formatting.")
                )
        );

        try {
            String response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization", "Bearer " + groqApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parse the raw query from Groq's response
            JsonNode root = new ObjectMapper().readTree(response);
            return root.path("choices").get(0).path("message").path("content").asText().trim();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate search query", e);
        }
    }

    private List<String> fetchUnsplashImages(String query) {
        String response = unsplashWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/photos")
                        .queryParam("query", query)
                        .queryParam("per_page", "3") // Get 3 images
                        .queryParam("client_id", unsplashAccessKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseUnsplashResponse(response);
    }

    private List<String> parseUnsplashResponse(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode results = root.path("results");

            List<String> imageUrls = new ArrayList<>();
            for (JsonNode result : results) {
                imageUrls.add(result.path("urls").path("regular").asText());
            }
            return imageUrls;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Unsplash response", e);
        }
    }
}
