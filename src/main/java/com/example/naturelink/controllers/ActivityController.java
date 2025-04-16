package com.example.naturelink.controllers;

import com.example.naturelink.entity.Activity;
import com.example.naturelink.services.IActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final IActivityService activityService;

    public ActivityController(IActivityService activityService) {
        this.activityService = activityService;
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
    @PostMapping("/add")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
        Activity savedActivity = activityService.addActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }

    // Update an activity
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Integer id, @RequestBody Activity activityDetails) {
        try {
            Activity updatedActivity = activityService.updateActivity(id, activityDetails);
            return ResponseEntity.ok(updatedActivity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Activity not found\"}");
        }
    }

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
}