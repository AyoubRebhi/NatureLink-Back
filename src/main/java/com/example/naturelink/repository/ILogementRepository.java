package com.example.naturelink.repository;

import com.example.naturelink.entity.Logement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILogementRepository extends JpaRepository<Logement, Long> {
    // Define custom query methods if needed
}
