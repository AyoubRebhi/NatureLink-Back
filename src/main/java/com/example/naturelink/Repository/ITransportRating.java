package com.example.naturelink.Repository;

import com.example.naturelink.Entity.TransportRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransportRating extends JpaRepository<TransportRating, Integer> {
}
