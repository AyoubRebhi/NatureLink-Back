package com.example.naturelink.entity;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User client; // Qui a réservé ?

    private Date dateReservation; // Date à laquelle la réservation a été faite

    private Date dateDebut; // Date de début du séjour

    private Date dateFin; // Date de fin du séjour

    private String statut; // Statut : "Confirmée", "Annulée", "En attente"

    private TypeReservation typeres;


    @ManyToOne
    @Nullable
    private Logement logementId;

    @ManyToOne
    @Nullable
    private Evenement eventId;

    @ManyToOne
    @Nullable
    private Restaurant RestaurantId;

    @ManyToOne
    @Nullable
    private Transport transpId;

    @ManyToOne
    @Nullable
    private Activite ActiviteId;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public TypeReservation getTyperes() {
        return typeres;
    }

    public void setTyperes(TypeReservation typeres) {
        this.typeres = typeres;
    }

    public Logement getLogementId() {
        return logementId;
    }

    public void setLogementId(Logement logementId) {
        logementId = logementId;
    }

    public Evenement getEventId() {
        return eventId;
    }

    public void setEventId(Evenement eventId) {
        eventId = eventId;
    }

    public Restaurant getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(Restaurant restaurantId) {
        RestaurantId = restaurantId;
    }

    public Transport getTranspId() { return transpId; }
    public void setTranspId(Transport transpId) { this.transpId = transpId; }


    // Set User
    public User getUser() {
        return client;
    }

    public void setUser(User user) {
        this.client = user;
    }

    public void setUserId(Integer userId) {
        if (this.client == null) {
            this.client = new User();
        }
        this.client.setId(userId);
    }
    public Activite getActiviteId() {
        return ActiviteId;
    }

    public void setActiviteId(Activite activiteId) {
        ActiviteId = activiteId;
    }
}
