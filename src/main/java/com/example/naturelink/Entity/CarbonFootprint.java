package com.example.naturelink.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CarbonFootprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double distance; // en km
    private String transportType;
    private double carbonFootprint; // en kg CO2
    private LocalDateTime date;

    private String departurePoint;
    private String arrivalPoint;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructeurs, getters et setters sont générés par Lombok @Data
}