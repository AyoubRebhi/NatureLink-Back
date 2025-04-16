    package com.example.naturelink.dto;

    import java.util.List;

    public class PackDTO {
        private String nom;
        private double prix;
        private String description;
        private List<Long> logements;
        private List<Long> restaurants;
        private List<Long> activities;
        private List<Long> transports;
        private List<Long> evenements;
        private Long userId;
        private Long id; // <--- Make sure this is here
        private double averageRating;



        // Getters and Setters remain the same
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public double getPrix() { return prix; }
        public void setPrix(double prix) { this.prix = prix; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public List<Long> getLogements() { return logements; }
        public void setLogements(List<Long> logements) { this.logements = logements; }
        public List<Long> getRestaurants() { return restaurants; }
        public void setRestaurants(List<Long> restaurants) { this.restaurants = restaurants; }
        public List<Long> getActivities() { return activities; }
        public void setActivities(List<Long> activities) { this.activities = activities; }
        public List<Long> getTransports() { return transports; }
        public void setTransports(List<Long> transports) { this.transports = transports; }
        public List<Long> getEvenements() { return evenements; }
        public void setEvenements(List<Long> evenements) { this.evenements = evenements; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }
    }
