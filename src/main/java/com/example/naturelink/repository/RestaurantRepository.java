package com.example.naturelink.repository;

import com.example.naturelink.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Define custom query methods if needed
}
