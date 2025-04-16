
package com.example.naturelink.Service;

import com.example.naturelink.Entity.Logement;

import java.util.List;
import java.util.Optional;

public interface ILogementService {
    List<Logement> getAllLogements();
    Optional<Logement> getLogementById(Integer id);
    Logement addLogement(Logement logement);
    Logement updateLogement(Integer id, Logement logementDetails);
    void deleteLogement(Integer id);
}
