package com.example.naturelink.services;

import com.example.naturelink.entity.Evenement;
import com.example.naturelink.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvenementService implements IEvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Override
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @Override
    public Optional<Evenement> getEvenementById(Long id) {
        return evenementRepository.findById(id);
    }

    @Override
    public Evenement addEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public Evenement updateEvenement(Long id, Evenement evenement) {
        if (evenementRepository.existsById(id)) {
            evenement.setId(id);
            return evenementRepository.save(evenement);
        }
        throw new RuntimeException("Evenement not found");
    }

    @Override
    public void deleteEvenement(Long id) {
        evenementRepository.deleteById(id);
    }
}
