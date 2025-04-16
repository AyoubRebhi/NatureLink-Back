package com.example.naturelink.Service;

import com.example.naturelink.Service.IPackService;
import com.example.naturelink.dto.PackDTO;
import com.example.naturelink.Entity.*;
import com.example.naturelink.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PackService implements IPackService {

    @Autowired private PackRepository packRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ILogementRepository logementRepository;
    @Autowired private ITransportRepository transportRepository;
    @Autowired private IActivityRepository activityRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private IEventRepository evenementRepository;

    @Override
    public List<PackDTO> getAllPacks() {
        List<Pack> packs = packRepository.findAll();

        return packs.stream().map(pack -> {
            PackDTO dto = new PackDTO();
            dto.setNom(pack.getNom());
            dto.setPrix(pack.getPrix());
            dto.setDescription(pack.getDescription());
            dto.setId(pack.getId());
            dto.setUserId(pack.getUser() != null ? pack.getUser().getId().longValue() : null);

            dto.setLogements(pack.getLogements().stream().map(Logement::getId).map(Long::valueOf).collect(Collectors.toList()));
            dto.setTransports(pack.getTransports().stream().map(Transport::getId).collect(Collectors.toList()));
            dto.setActivities(pack.getActivities().stream().map(Activity::getId).map(Long::valueOf).collect(Collectors.toList()));
            dto.setRestaurants(pack.getRestaurants().stream().map(Restaurant::getId).collect(Collectors.toList()));
            dto.setEvenements(pack.getEvenements().stream().map(Event::getId).collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pack> getPackById(Long id) {
        return packRepository.findById(id);
    }

    public Pack addPack(PackDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("PackDTO cannot be null");
        }

        Pack pack = new Pack();
        pack.setNom(dto.getNom());
        pack.setDescription(dto.getDescription());
        pack.setPrix(dto.getPrix());

        int associatedCount = 0;

        // Logements
        if (dto.getLogements() != null && !dto.getLogements().isEmpty()) {
            List<Logement> logements = logementRepository.findAllById(dto.getLogements());
            pack.setLogements(logements);
            associatedCount++;
        }

        // Restaurants
        if (dto.getRestaurants() != null && !dto.getRestaurants().isEmpty()) {
            List<Restaurant> restaurants = restaurantRepository.findAllById(dto.getRestaurants());
            pack.setRestaurants(restaurants);
            associatedCount++;
        }

        // Activities
        if (dto.getActivities() != null && !dto.getActivities().isEmpty()) {
            List<Activity> activities = activityRepository.findAllById(dto.getActivities());
            pack.setActivities(activities);
            associatedCount++;
        }

        // Transports - convert to correct ID type
        if (dto.getTransports() != null && !dto.getTransports().isEmpty()) {
            List<Transport> transports = transportRepository.findAllById(
                    dto.getTransports().stream()
                            .map(Integer::longValue) // Convert Integer to Long if needed
                            .collect(Collectors.toList())
            );
            pack.setTransports(transports);
            associatedCount++;
        }

        // Evenements - convert to correct ID type
        if (dto.getEvenements() != null && !dto.getEvenements().isEmpty()) {
            List<Event> evenements = evenementRepository.findAllById(
                    dto.getEvenements().stream()
                            .map(Integer::longValue) // Convert Integer to Long if needed
                            .collect(Collectors.toList())
            );
            pack.setEvenements(evenements);
            associatedCount++;
        }

        if (associatedCount < 2) {
            throw new IllegalArgumentException("A pack must contain at least two associated elements.");
        }

        User user = userRepository.findById(dto.getUserId().intValue())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        pack.setUser(user);

        return packRepository.save(pack);
    }

    @Override
    public Pack updatePack(Long id, PackDTO dto) {
        Pack existingPack = packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found"));

        existingPack.setNom(dto.getNom());
        existingPack.setPrix(dto.getPrix());
        existingPack.setDescription(dto.getDescription());

        // Update associations with proper ID type conversion
        if (dto.getLogements() != null) {
            existingPack.setLogements(logementRepository.findAllById(dto.getLogements()));
        }
        if (dto.getTransports() != null) {
            existingPack.setTransports(transportRepository.findAllById(
                    dto.getTransports().stream()
                            .map(Integer::longValue)
                            .collect(Collectors.toList())
            ));
        }
        if (dto.getActivities() != null) {
            existingPack.setActivities(activityRepository.findAllById(dto.getActivities()));
        }
        if (dto.getRestaurants() != null) {
            existingPack.setRestaurants(restaurantRepository.findAllById(dto.getRestaurants()));
        }
        if (dto.getEvenements() != null) {
            existingPack.setEvenements(evenementRepository.findAllById(
                    dto.getEvenements().stream()
                            .map(Integer::longValue)
                            .collect(Collectors.toList())
            ));
        }

        return packRepository.save(existingPack);
    }

    @Override
    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }
}