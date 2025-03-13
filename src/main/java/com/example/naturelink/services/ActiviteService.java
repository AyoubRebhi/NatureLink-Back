package com.example.naturelink.services;

import com.example.naturelink.entity.Activite;
import com.example.naturelink.repository.ActiviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService implements IActiviteService {

    @Autowired
    private ActiviteRepository activiteRepository;

    @Override
    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    @Override
    public Optional<Activite> getActiviteById(Integer id) {
        return activiteRepository.findById(id);
    }

    @Override
    public Activite addActivite(Activite activite) {
        return activiteRepository.save(activite);
    }

    @Override
    public Activite updateActivite(Integer id, Activite activite) {
        if (activiteRepository.existsById(id)) {
            activite.setId(id);
            return activiteRepository.save(activite);
        }
        throw new RuntimeException("Activite not found");
    }

    @Override
    public void deleteActivite(Integer id) {
        activiteRepository.deleteById(id);
    }
}
