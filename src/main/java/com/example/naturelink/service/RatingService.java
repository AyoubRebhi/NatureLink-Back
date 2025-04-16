package com.example.naturelink.service;

import com.example.naturelink.entity.Pack;
import com.example.naturelink.entity.Rating;
import com.example.naturelink.repository.PackRepository;
import com.example.naturelink.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private PackRepository packRepository;

    // Method to add a rating for a specific pack
    public Rating addRating(Long packId, int ratingValue, Long userId) {
        // Ensure the pack exists
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found with id: " + packId));

        // Create a new Rating entity
        Rating rating = new Rating();
        rating.setRating(ratingValue);
        rating.setPack(pack);

        // Optionally, you could also associate the rating with a specific user
        // User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // rating.setUser(user);

        // Save the rating
        return ratingRepository.save(rating);
    }

    // Method to calculate the average rating for a pack (by reservation)
    public double getAverageRatingForPack(Long packId) {
        List<Rating> ratings = ratingRepository.findByReservationId(packId);
        if (ratings.isEmpty()) {
            return 0;  // No ratings, return 0
        }
        return ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0);  // Return average, or 0 if no ratings
    }

    // Add other methods to handle rating creation, etc.
}
