package com.example.naturelink.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plats;
    private float prixMoyen;

    // Many-to-One relationship with Restaurant
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for 'plats'
    public String getPlats() {
        return plats;
    }

    public void setPlats(String plats) {
        this.plats = plats;
    }

    // Getter and Setter for 'prixMoyen'
    public float getPrixMoyen() {
        return prixMoyen;
    }

    public void setPrixMoyen(float prixMoyen) {
        this.prixMoyen = prixMoyen;
    }

    // Getter and Setter for 'restaurant'
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // Constructor, if necessary
    public Menu() {
    }

    public Menu(Long id, String plats, float prixMoyen, Restaurant restaurant) {
        this.id = id;
        this.plats = plats;
        this.prixMoyen = prixMoyen;
        this.restaurant = restaurant;
    }
}