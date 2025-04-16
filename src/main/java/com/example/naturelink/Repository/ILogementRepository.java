package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Logement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILogementRepository extends JpaRepository<Logement, Long> {
    // Define custom query methods if needed
}
