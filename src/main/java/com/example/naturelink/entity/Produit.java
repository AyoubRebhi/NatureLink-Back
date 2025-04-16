package com.example.naturelink.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
private int cost;
private int offre;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne
    @JoinColumn(name = "boutique_id")
    @JsonBackReference
    private Boutique boutique;

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getOffre() {
        return offre;
    }

    public void setOffre(int offre) {
        this.offre = offre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }
}
