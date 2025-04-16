package com.example.naturelink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;
    private Integer capacity;
    private Float pricePerKm;
    private Boolean available;
    private String imgUrl; // New field for Cloudinary image URL
    private String description; // ✅ New field for promotional description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Float getPricePerKm() {
        return pricePerKm;
    }

    public Boolean getAvailable() {
        return available;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setPricePerKm(Float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
