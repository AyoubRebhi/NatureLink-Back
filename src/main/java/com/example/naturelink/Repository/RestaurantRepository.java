package com.example.naturelink.Repository;
import com.example.naturelink.Entity.*;

import com.example.naturelink.Entity.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Define custom query methods if needed
}
