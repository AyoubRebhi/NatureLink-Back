package com.example.naturelink.dto;

import java.util.Date;
import java.util.List;

public class ReservationDTO {

    private Long userId;

    private Date dateDebut;
    private Date dateFin;
    private Long logementId;
    private Long eventId;
    private Long restaurantId;
    private Integer transportId;
    private Integer activityId;
    private String statut;
    private Long id; // Ensure this is here

    // New fields
    private Integer numClients; // Number of clients
    private Integer numRooms; // Number of rooms (only for logement reservations)
    private List<String> clientNames; // List of client names for each client in the reservation
    private String typeres;

    // Constructor
    public ReservationDTO(Long userId,  Date dateDebut, Date dateFin,
                          Long logementId, Long eventId, Long restaurantId,
                          Integer transportId, Integer activityId, String statut,
                          Long id, Integer numClients, Integer numRooms, List<String> clientNames) {
        this.userId = userId;

        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.logementId = logementId;
        this.eventId = eventId;
        this.restaurantId = restaurantId;
        this.transportId = transportId;
        this.activityId = activityId;
        this.statut = statut;
        this.id = id;
        this.numClients = numClients;
        this.numRooms = numRooms;
        this.clientNames = clientNames;
    }

    // Default constructor (required for deserialization)
    public ReservationDTO() {
    }

    // Getter and Setter methods

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getLogementId() {
        return logementId;
    }

    public void setLogementId(Long logementId) {
        this.logementId = logementId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for numClients
    public Integer getNumClients() {
        return numClients;
    }

    public void setNumClients(Integer numClients) {
        this.numClients = numClients;
    }

    // Getter and Setter for numRooms
    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    // Getter and Setter for clientNames (list of client names)
    public List<String> getClientNames() {
        return clientNames;
    }

    public void setClientNames(List<String> clientNames) {
        this.clientNames = clientNames;
    }

    public String getTyperes() {
        return typeres;
    }

    public void setTyperes(String typeres) {
        this.typeres = typeres;
    }
}
