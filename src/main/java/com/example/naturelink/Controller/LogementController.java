package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Logement;
import com.example.naturelink.Service.LogementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logements")
public class LogementController {

    @Autowired
    private LogementService logementService;

    // Get all logements
    @GetMapping
    public List<Logement> getAllLogements() {
        return logementService.getAllLogements();
    }

    // Get logement by ID
    @GetMapping("/{id}")
    public ResponseEntity<Logement> getLogementById(@PathVariable Integer id) {
        Optional<Logement> logement = logementService.getLogementById(id);
        return logement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new logement
    @PostMapping
    public Logement addLogement(@RequestBody Logement logement) {
        return logementService.addLogement(logement);
    }

    // Update an existing logement
    @PutMapping("/{id}")
    public ResponseEntity<Logement> updateLogement(@PathVariable Integer id, @RequestBody Logement logementDetails) {
        Logement updatedLogement = logementService.updateLogement(id, logementDetails);
        return ResponseEntity.ok(updatedLogement);
    }

    // Delete a logement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogement(@PathVariable Integer id) {
        logementService.deleteLogement(id);
        return ResponseEntity.noContent().build();
    }
}