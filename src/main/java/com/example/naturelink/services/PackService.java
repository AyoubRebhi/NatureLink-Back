package com.example.naturelink.services;

import com.example.naturelink.entity.*;
import com.example.naturelink.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackService implements IPackService {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ILogementRepository logementRepository;

    @Autowired
    private ITransportRepository transportRepository;

    @Autowired
    private IActivityRepository activityRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private EvenementRepository evenementRepository;

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
        // Ensure the user exists
        if (pack.getUser() != null && pack.getUser().getId() != null) {
            User user = userRepository.findById(pack.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            pack.setUser(user);
        } else {
            throw new RuntimeException("User ID is required to create a Pack");
        }

        // Ensure related entities exist and fetch them
        fetchAndSetRelatedEntities(pack);

        // Counting the number of non-null relationships
        int validRelations = countValidRelations(pack);

        // Ensure the pack has at least two elements
        if (validRelations < 2) {
            throw new RuntimeException("Pack must include at least two elements (Logement, Transport, Activity, Restaurant, or Evenement).");
        }

        // Save the pack
        return packRepository.save(pack);
    }


    // Helper method to fetch related entities
    private void fetchAndSetRelatedEntities(Pack pack) {
        // Fetch and set Logements
        if (pack.getLogements() != null) {
            for (Logement logement : pack.getLogements()) {
                logementRepository.findById(Long.valueOf(logement.getId()))
                        .ifPresent(existingLogement -> logement.setId(existingLogement.getId()));
            }
        }

        // Fetch and set Transports
        if (pack.getTransports() != null) {
            for (Transport transport : pack.getTransports()) {
                transportRepository.findById(transport.getId())
                        .ifPresent(existingTransport -> transport.setId(existingTransport.getId()));
            }
        }

        // Fetch and set Activities
        if (pack.getActivities() != null) {
            for (Activity activity : pack.getActivities()) {
                activityRepository.findById(activity.getId())
                        .ifPresent(existingActivity -> activity.setId(existingActivity.getId()));
            }
        }

        // Fetch and set Restaurants
        if (pack.getRestaurants() != null) {
            for (Restaurant restaurant : pack.getRestaurants()) {
                restaurantRepository.findById(restaurant.getId())
                        .ifPresent(existingRestaurant -> restaurant.setId(existingRestaurant.getId()));
            }
        }

        // Fetch and set Evenements
        if (pack.getEvenements() != null) {
            for (Evenement evenement : pack.getEvenements()) {
                evenementRepository.findById(evenement.getId())
                        .ifPresent(existingEvenement -> evenement.setId(existingEvenement.getId()));
            }
        }
    }

    // Helper method to count valid relations
    private int countValidRelations(Pack pack) {
        int validRelations = 0;

        if (pack.getLogements() != null && !pack.getLogements().isEmpty()) {
            validRelations++;
        }

        if (pack.getTransports() != null && !pack.getTransports().isEmpty()) {
            validRelations++;
        }

        if (pack.getActivities() != null && !pack.getActivities().isEmpty()) {
            validRelations++;
        }

        if (pack.getRestaurants() != null && !pack.getRestaurants().isEmpty()) {
            validRelations++;
        }

        if (pack.getEvenements() != null && !pack.getEvenements().isEmpty()) {
            validRelations++;
        }

        return validRelations;
    }

    @Override
    public Pack updatePack(Long id, Pack pack) {
        if (packRepository.existsById(id)) {
            pack.setId(id);
            // Handle relations
            if (pack.getUser() != null) {
                pack.setUserId(pack.getUser().getId());
            }
            return packRepository.save(pack);
        }
        throw new RuntimeException("Pack not found");
    }

    @Override
    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }
}
