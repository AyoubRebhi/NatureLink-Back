package com.example.naturelink.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String location;
    private String date;
    private int nbrplace;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    private String founder;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNbrplace() {
        return nbrplace;
    }

    public void setNbrplace(int nbrplace) {
        this.nbrplace = nbrplace;
    }

    public int getId() {
        return id;
    }
}
