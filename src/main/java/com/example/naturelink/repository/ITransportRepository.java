package com.example.naturelink.repository;

import com.example.naturelink.entity.Logement;
import com.example.naturelink.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransportRepository extends JpaRepository<Transport, Long> {
    // Define custom query methods if needed
}
