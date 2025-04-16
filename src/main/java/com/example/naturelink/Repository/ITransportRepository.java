package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransportRepository extends JpaRepository<Transport, Long> {
    // Define custom query methods if needed
}