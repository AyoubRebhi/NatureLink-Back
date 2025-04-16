package com.example.naturelink.Service;


import com.example.naturelink.Entity.Logement;
import com.example.naturelink.Repository.ILogementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogementService implements ILogementService {

    @Autowired
    private ILogementRepository logementRepository;

    // Get all logements
    public List<Logement> getAllLogements() {
        return logementRepository.findAll();
    }

    // Get logement by ID
    public Optional<Logement> getLogementById(Integer id) {
        return logementRepository.findById(Long.valueOf(id));
    }

    // Add a new logement
    public Logement addLogement(Logement logement) {
        return logementRepository.save(logement);
    }

    // Update an existing logement
    public Logement updateLogement(Integer id, Logement logementDetails) {
        Logement logement = logementRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Logement not found with id: " + id));

        logement.setTitre(logementDetails.getTitre());
        logement.setDescription(logementDetails.getDescription());
        logement.setLocation(logementDetails.getLocation());
        logement.setEquipment(logementDetails.getEquipment());
        logement.setPrice(logementDetails.getPrice());
        logement.setImage(logementDetails.getImage());
        logement.setProprietarield(logementDetails.getProprietarield());
        logement.setPhone(logementDetails.getPhone());
        logement.setEmail(logementDetails.getEmail());
        logement.setSocialMedia(logementDetails.getSocialMedia());

        return logementRepository.save(logement);
    }

    // Delete a logement
    public void deleteLogement(Integer id) {
        logementRepository.deleteById(Long.valueOf(id));
    }
}
