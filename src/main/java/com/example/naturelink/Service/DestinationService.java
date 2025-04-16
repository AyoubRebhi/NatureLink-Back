package com.example.naturelink.Service;

import com.example.naturelink.Entity.Destination;
import com.example.naturelink.Repository.IDestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

    @Autowired
    private IDestinationRepository destinationRepository;

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }



    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }
}
