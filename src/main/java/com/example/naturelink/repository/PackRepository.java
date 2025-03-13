package com.example.naturelink.repository;

import com.example.naturelink.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository extends JpaRepository<Pack, Long> {
    // Define custom query methods if needed
}
