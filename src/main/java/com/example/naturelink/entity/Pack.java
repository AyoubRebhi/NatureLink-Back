package com.example.naturelink.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pack")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private double prix;
    private String description;

    @Nullable
    @ManyToOne
    private User user;

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Pack_Logement",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "logement_id")
    )
    private List<Logement> logements = new ArrayList<>();

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Pack_Transport",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "transport_id")
    )
    private List<Transport> transports = new ArrayList<>();

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Pack_Activite",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "activite_id")
    )
    private List<Activity> activities = new ArrayList<>();

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Pack_Restaurant",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private List<Restaurant> restaurants = new ArrayList<>();

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Pack_Evenement",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns = @JoinColumn(name = "evenement_id")
    )
    private List<Event> evenements = new ArrayList<>();

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Logement> getLogements() {
        return logements;
    }

    public void setLogements(List<Logement> logements) {
        this.logements = (logements != null) ? logements : new ArrayList<>();
    }

    public List<Transport> getTransports() {
        return transports;
    }

    public void setTransports(List<Transport> transports) {
        this.transports = (transports != null) ? transports : new ArrayList<>();
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = (activities != null) ? activities : new ArrayList<>();
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = (restaurants != null) ? restaurants : new ArrayList<>();
    }

    public List<Event> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Event> evenements) {
        this.evenements = (evenements != null) ? evenements : new ArrayList<>();
    }

    public void setUserId(Integer userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
    }
}