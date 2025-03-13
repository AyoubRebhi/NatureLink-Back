package com.example.naturelink.repository;

import com.example.naturelink.entity.Activite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiviteRepository extends JpaRepository<Activite, Integer> {
    // Define custom query methods if needed
}
