package com.example.naturelink.Controller;

import com.example.naturelink.Entity.CarbonFootprint;
import com.example.naturelink.Service.CarbonFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.naturelink.Entity.CarbonFootprint;
import com.example.naturelink.Service.CarbonFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/footprints")
public class CarbonFootprintController {

    @Autowired
    private CarbonFootprintService footprintService;

    @PostMapping
    public ResponseEntity<CarbonFootprint> createFootprint(@RequestBody CarbonFootprint footprint) {
        footprint.setDate(LocalDateTime.now());
        CarbonFootprint saved = footprintService.saveFootprint(footprint);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/by-transport")
    public ResponseEntity<List<CarbonFootprint>> getByTransport(@RequestParam String transportType) {
        return ResponseEntity.ok(footprintService.getFootprintsByTransport(transportType));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<CarbonFootprint>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(footprintService.getFootprintsByUser(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<CarbonFootprint>> getHistory(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String transportType) {

        if (userId != null && transportType != null) {
            return ResponseEntity.ok(footprintService.getFootprintsByUserAndTransport(userId, transportType));
        } else if (userId != null) {
            return ResponseEntity.ok(footprintService.getFootprintsByUser(userId));
        } else if (transportType != null) {
            return ResponseEntity.ok(footprintService.getFootprintsByTransport(transportType));
        } else {
            return ResponseEntity.ok(footprintService.getAllFootprints());
        }
    }
}