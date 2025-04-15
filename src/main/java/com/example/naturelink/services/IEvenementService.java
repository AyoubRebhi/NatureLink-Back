package com.example.naturelink.services;

import com.example.naturelink.entity.Event;

import java.util.List;
import java.util.Optional;

public interface IEvenementService {

    List<Event> getAllEvenements();

    Optional<Event> getEvenementById(Long id);

    Event addEvenement(Event evenement);

    Event updateEvenement(Long id, Event evenement);

    void deleteEvenement(Long id);
}
