package com.example.naturelink.repository;

import com.example.naturelink.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivityRepository extends JpaRepository<Activity, Integer> {
    // Define custom query methods if needed
}
