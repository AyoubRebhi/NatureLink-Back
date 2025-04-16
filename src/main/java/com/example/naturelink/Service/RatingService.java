package com.example.naturelink.Service;

import com.example.naturelink.Entity.Pack;
import com.example.naturelink.Entity.Rating;
import com.example.naturelink.Repository.PackRepository;
import com.example.naturelink.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private PackRepository packRepository;

    public Rating addRating(Long packId, int ratingValue, Long userId) {
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found with id: " + packId));
        Rating rating = new Rating();
        rating.setRating(ratingValue);
        rating.setPack(pack);
        return ratingRepository.save(rating);
    }

    public double getAverageRatingForPack(Long packId) {
        List<Rating> ratings = ratingRepository.findByPackId(packId); // Updated
        if (ratings.isEmpty()) {
            return 0;
        }
        return ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0);
    }
}