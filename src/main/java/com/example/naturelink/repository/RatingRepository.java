package com.example.naturelink.repository;

import com.example.naturelink.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Optionally, you can add methods to fetch ratings by reservation ID
    List<Rating> findByReservationId(Long reservationId);
}
