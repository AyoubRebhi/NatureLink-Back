package com.example.naturelink.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    @JsonIgnore  // Ignorer cette propriété lors de la sérialisation JSON
    private List<Food> foods;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    @JsonIgnore  // Ignorer cette propriété lors de la sérialisation JSON
    private List<Clothing> clothingItems;

    // Getters et Setters sont générés par Lombok
}
