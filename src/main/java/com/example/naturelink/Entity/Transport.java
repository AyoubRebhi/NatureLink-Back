package com.example.naturelink.Entity;

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
}
