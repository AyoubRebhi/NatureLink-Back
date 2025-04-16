package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivityRepository extends JpaRepository<Activity, Long> {
    // Define custom query methods if needed
}
