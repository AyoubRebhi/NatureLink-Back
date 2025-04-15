package com.example.naturelink.repository;

import com.example.naturelink.entity.TransportRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransportRating extends JpaRepository<TransportRating, Integer> {
}
