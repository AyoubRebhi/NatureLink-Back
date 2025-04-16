package com.example.naturelink.Entity;

import com.example.naturelink.Entity.User;
import com.example.naturelink.Entity.*;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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


    private Date dateDebut; // Date de début du séjour

    private Date dateFin; // Date de fin du séjour

    private String statut; // Statut : "Confirmée", "Annulée", "En attente"

    @Enumerated(EnumType.STRING)
    private TypeReservation typeres;

    private Integer numClients; // Number of clients involved in the reservation

    @ElementCollection
    @CollectionTable(name = "reservation_client_names", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "client_names    ") // For storing the names of clients in a list
    private List<String> clientNames; // List of client names for each client in the reservation

    private Integer numRooms; // Number of rooms (only for logement type)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logement_id")
    @Nullable
    private Logement logementId;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "event_id")
    private Event eventId;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "transport_id")
    private Transport transpId;

    @ManyToOne(cascade = CascadeType.ALL)
    @Nullable
    @JoinColumn(name = "activite_id")
    private Activity activityId;

    // Getter and Setter for numClients and numRooms
    public Integer getNumClients() {
        return numClients;
    }

    public void setNumClients(Integer numClients) {
        this.numClients = numClients;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    // Getter and Setter methods for clientNames (List of client names)
    public List<String> getClientNames() {
        return clientNames;
    }

    public void setClientNames(List<String> clientNames) {
        this.clientNames = clientNames;
    }

    // Existing Getter and Setter methods for other fields
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
        this.logementId = logementId;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Transport getTranspId() {
        return transpId;
    }

    public void setTranspId(Transport transpId) {
        this.transpId = transpId;
    }

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }
    public void setUserId(Integer userId) {
        if (this.client == null) {
            this.client = new User();
        }
        this.client.setId(userId);
    }

}


