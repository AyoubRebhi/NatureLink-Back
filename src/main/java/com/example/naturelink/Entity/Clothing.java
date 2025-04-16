package com.example.naturelink.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;
    private String season; // Winter, Summer, etc.

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination   ;

    // Getters and Setters
}
