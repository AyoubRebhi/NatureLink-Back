package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByDestinationNom(String destination);
    List<Food> findBySeason(String season);
    List<Food> findBySeasonAndDestinationId(String season, Long destinationId);


}

