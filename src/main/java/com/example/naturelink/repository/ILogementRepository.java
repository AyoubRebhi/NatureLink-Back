package com.example.naturelink.Repository;
import com.example.naturelink.Entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILogementRepository extends JpaRepository<Logement, Long> {
    // Define custom query methods if needed
}
