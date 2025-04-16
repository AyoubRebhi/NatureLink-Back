package com.example.naturelink.Controller;

<<<<<<< HEAD:src/main/java/com/example/naturelink/Controller/TransportRatingController.java
import com.example.naturelink.Entity.TransportRating;
import com.example.naturelink.Service.ITransportRatingService;
=======
import com.example.naturelink.Entity.*;
import com.example.naturelink.Service.*;
>>>>>>> 4b29ae4d41c61db53652921e32fd68b517d8d2d9:src/main/java/com/example/naturelink/controller/TransportRatingController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@CrossOrigin("*")
public class TransportRatingController {

    @Autowired
    private ITransportRatingService ratingService;

    // ➕ Add new rating
    @PostMapping("/add")
    public ResponseEntity<TransportRating> addRating(@RequestBody TransportRating rating) {
        TransportRating savedRating = ratingService.addRating(rating);
        return ResponseEntity.ok(savedRating);
    }

    // 📋 Get all ratings
    @GetMapping
    public ResponseEntity<List<TransportRating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }

    // 🔍 Get a rating by its ID
    @GetMapping("/{id}")
    public ResponseEntity<TransportRating> getRatingById(@PathVariable Integer id) {
        return ratingService.getRatingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🚌 Get all ratings for a specific transport
    @GetMapping("/transport/{transportId}")
    public ResponseEntity<List<TransportRating>> getRatingsByTransport(@PathVariable Integer transportId) {
        return ResponseEntity.ok(ratingService.getRatingsByTransportId(transportId));
    }

    // ⭐ Get average rating for a transport
    @GetMapping("/avg/{transportId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Integer transportId) {
        return ResponseEntity.ok(ratingService.getAverageRatingForTransport(transportId));
    }

    // ❌ Delete a rating
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Integer id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
