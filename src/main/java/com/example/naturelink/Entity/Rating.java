package com.example.naturelink.Entity;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private int rating;  // Rating value from 1 to 5 stars

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // The user who gave the rating
    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Pack getPack() {
        return pack;
    }
}