package com.example.naturelink.repository;

import com.example.naturelink.entity.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    // Define custom query methods if needed
}
