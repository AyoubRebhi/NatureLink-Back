package com.example.naturelink.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Logement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private String description;
    private String location;

    @Enumerated(EnumType.STRING)
    private EquipementType equipment;

    private Float price;
    private String image;
    private Integer proprietarield;

    private String phone;
    private String email;
    private String socialMedia;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EquipementType getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipementType equipment) {
        this.equipment = equipment;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProprietarield() {
        return proprietarield;
    }

    public void setProprietarield(Integer proprietarield) {
        this.proprietarield = proprietarield;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    @Override
    public String toString() {
        return "Logement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", equipment=" + equipment +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", proprietarield=" + proprietarield +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", socialMedia='" + socialMedia + '\'' +
                '}';
    }

}