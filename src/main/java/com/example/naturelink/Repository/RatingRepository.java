package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Optionally, you can add methods to fetch ratings by reservation ID
    List<Rating> findByPackId(Long packId);  }