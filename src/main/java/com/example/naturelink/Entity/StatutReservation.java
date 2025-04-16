package com.example.naturelink.Entity;

public enum StatutReservation {
    CONFIRMEE("Confirmée"),
    ANNULEE("Annulée"),
    EN_ATTENTE("En attente");

    private final String label;

    StatutReservation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
