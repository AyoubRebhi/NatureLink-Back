package com.example.naturelink.Controller;

<<<<<<< HEAD:src/main/java/com/example/naturelink/Controller/ActivityController.java
import com.example.naturelink.Entity.Activity;
import com.example.naturelink.Service.ActivityService;
import com.example.naturelink.Service.GroqService;
import com.example.naturelink.Service.IActivityService;
=======
import com.example.naturelink.Entity.*;
import com.example.naturelink.Service.*;
>>>>>>> 4b29ae4d41c61db53652921e32fd68b517d8d2d9:src/main/java/com/example/naturelink/controller/ActivityController.java
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.naturelink.dto.ActivityDTO;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*") // Optional: allow frontend access
@RequestMapping("/activities")
public class ActivityController {

    private final IActivityService activityService;
    private final com.example.naturelink.Service.ActivityService activityServiceImpl;

    private final com.example.naturelink.Service.GroqService groqService;
    public ActivityController(IActivityService activityService, ActivityService activityServiceImpl, GroqService groqService) {
        this.activityService = activityService;
        this.activityServiceImpl = activityServiceImpl;
        this.groqService = groqService;
    }
    @PostMapping("/get-activity-images")
    public ResponseEntity<?> getActivityImages(@RequestBody Map<String, String> body) {
        try {
            String description = body.get("description");
            if (description == null || description.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Description is required"));
            }

            List<String> images = groqService.getActivityImages(description);
            return ResponseEntity.ok(Map.of("images", images));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Failed to process request",
                    "details", e.getMessage()
            ));
        }
    }
    @PostMapping("/generate")
    public ResponseEntity<String> generateActivity(@RequestBody Map<String, String> promptParams) {
        try {
            String response = groqService.generateActivityWithPrompt(promptParams);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate activity: " + e.getMessage());
        }
    }

    // Get all activities
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    // Get activity by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable Integer id) {
        Optional<Activity> activity = activityService.getActivityById(id);
        return activity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((Activity) Map.of("message", "Transport not found")));
    }

    // Add new activity

    // Update an activity

    // Delete an activity
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Integer id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok("{\"message\": \"Activity deleted successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Activity not found\"}");
        }
    }
    @PostMapping(value = "/add-images", consumes = {"multipart/form-data"})
    public ResponseEntity<Activity> addActivityWithImages(
            @RequestPart("activity") Activity activity,
            @RequestPart("images") List<MultipartFile> imageFiles) {

        try {
            Activity saved = activityServiceImpl.addActivityWithImages(activity, imageFiles);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @PutMapping(value = "/update-images/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Activity> updateActivityWithImages(
            @PathVariable Integer id,
            @RequestPart("activity") Activity activity,
            @RequestPart(value = "images", required = false) List<MultipartFile> imageFiles) {

        try {
            Activity updated = activityServiceImpl.updateActivityWithImages(id, activity, imageFiles);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Recommandation Controller
    @PostMapping("/recommend")
    public ResponseEntity<?> recommendActivities(@RequestBody Map<String, String> userInput) {
        List<Activity> allActivities = activityService.getAllActivities();

        // 🔁 Convert to DTOs for AI microservice
        List<ActivityDTO> dtoList = allActivities.stream().map(activity -> {
            ActivityDTO dto = new ActivityDTO();
            dto.setName(activity.getName());
            dto.setDescription(activity.getDescription());
            dto.setLocation(activity.getLocation());
            dto.setDuration(activity.getDuration());
            dto.setPrice(activity.getPrice());
            dto.setMaxParticipants(activity.getMaxParticipants());
            dto.setDifficultyLevel(activity.getDifficultyLevel());
            dto.setType(activity.getType());
            dto.setMood(activity.getMood());
            dto.setTags(activity.getTags());
            dto.setRequiredEquipment(activity.getRequiredEquipment());
            return dto;
        }).collect(Collectors.toList());

        // 🌐 Call Python recommender
        try {
            WebClient client = WebClient.create("http://localhost:5005");
            return client.post()
                    .uri("/recommend")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(Map.of(
                            "mood_input", userInput.get("mood_input"),
                            "activities", dtoList
                    ))
                    .retrieve()
                    .toEntity(String.class)
                    .block();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Recommendation engine error: " + e.getMessage());
        }
    }
}
