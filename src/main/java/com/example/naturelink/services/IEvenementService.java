package com.example.naturelink.services;

import com.example.naturelink.entity.Evenement;

import java.util.List;
import java.util.Optional;

public interface IEvenementService {

    List<Evenement> getAllEvenements();

    Optional<Evenement> getEvenementById(Long id);

    Evenement addEvenement(Evenement evenement);

    Evenement updateEvenement(Long id, Evenement evenement);

    void deleteEvenement(Long id);
}
