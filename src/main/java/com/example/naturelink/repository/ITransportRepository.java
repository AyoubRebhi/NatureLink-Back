package com.example.naturelink.repository;

import com.example.naturelink.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransportRepository extends JpaRepository<Transport, Integer> {
}
