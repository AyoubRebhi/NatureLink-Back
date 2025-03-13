package com.example.naturelink.services;

import com.example.naturelink.entity.Logement;
import com.example.naturelink.repository.LogementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogementService implements ILogementService {

    @Autowired
    private LogementRepository logementRepository;

    @Override
    public List<Logement> getAllLogements() {
        return logementRepository.findAll();
    }

    @Override
    public Optional<Logement> getLogementById(Long id) {
        return logementRepository.findById(id);
    }

    @Override
    public Logement addLogement(Logement logement) {
        return logementRepository.save(logement);
    }

    @Override
    public Logement updateLogement(Long id, Logement logement) {
        if (logementRepository.existsById(id)) {
            logement.setId(id);
            return logementRepository.save(logement);
        }
        throw new RuntimeException("Logement not found");
    }

    @Override
    public void deleteLogement(Long id) {
        logementRepository.deleteById(id);
    }
}
