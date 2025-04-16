package com.example.naturelink.repository;

import com.example.naturelink.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IActivityRepository extends JpaRepository<Activity, Long> {
    // Define custom query methods if needed
}
