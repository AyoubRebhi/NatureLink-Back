package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepository extends JpaRepository<Pack, Long> {
    // Define custom query methods if needed
}
