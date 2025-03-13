package com.example.naturelink.services;

import com.example.naturelink.entity.Pack;
import com.example.naturelink.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackService implements IPackService {

    @Autowired
    private PackRepository packRepository;

    @Override
    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }

    @Override
    public Optional<Pack> getPackById(Long id) {
        return packRepository.findById(id);
    }

    @Override
    public Pack addPack(Pack pack) {
        // Handle relations
        if (pack.getUser() != null) {
            pack.setUserId(pack.getUser().getId());
        }
        // Removed reservation handling
        return packRepository.save(pack);
    }

    @Override
    public Pack updatePack(Long id, Pack pack) {
        if (packRepository.existsById(id)) {
            pack.setId(id);
            // Handle relations
            if (pack.getUser() != null) {
                pack.setUserId(pack.getUser().getId());
            }
            // Removed reservation handling
            return packRepository.save(pack);
        }
        throw new RuntimeException("Pack not found");
    }

    @Override
    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }
}
