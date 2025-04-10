package com.example.naturelink.dto;

import java.util.Date;

public class ReservationDTO {
    private Long userId;
    private String username;
    private Date dateDebut;
    private Date dateFin;
    private Long logementId;
    private Long eventId;
    private Long restaurantId;
    private Integer transportId;
    private Integer activityId;
    private String statut;
    private Long id; // <--- Make sure this is here

    // Constructor
    public ReservationDTO(Integer userId, String username, Date dateDebut, Date dateFin,
                          Long logementId, Long eventId, Long restaurantId,
                          Integer transportId, Integer activityId, String statut,Long id) {
        this.userId = Long.valueOf(userId);
        this.username = username;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.logementId = logementId;
        this.eventId = eventId;
        this.restaurantId = restaurantId;
        this.transportId = transportId;
        this.activityId = activityId;
        this.statut = statut;
        this.id = id ;
    }

    // Default constructor (required for deserialization)
    public ReservationDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
