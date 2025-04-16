package com.example.naturelink.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class TransportRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer rating;

    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_id")
    @JsonIgnoreProperties("ratings")
    private Transport transport;

    @Transient
    private Integer transportId;
    @PostLoad
    private void fillTransientId() {
        if (transport != null) {
            this.transportId = transport.getId();
        }
    }

    // Getters and Setters 👇

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportRating that = (TransportRating) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(rating, that.rating) && Objects.equals(comment, that.comment) && Objects.equals(transport, that.transport) && Objects.equals(transportId, that.transportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, rating, comment, transport, transportId);
    }
}
