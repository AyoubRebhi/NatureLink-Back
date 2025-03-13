package com.example.naturelink.services;

import com.example.naturelink.entity.Activite;

import java.util.List;
import java.util.Optional;

public interface IActiviteService {

    List<Activite> getAllActivites();

    Optional<Activite> getActiviteById(Integer id);

    Activite addActivite(Activite activite);

    Activite updateActivite(Integer id, Activite activite);

    void deleteActivite(Integer id);
}
