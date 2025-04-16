package com.example.naturelink.Service;

import com.example.naturelink.Entity.Clothing;
import com.example.naturelink.Entity.Destination;
import com.example.naturelink.Repository.IDestinationRepository;
import com.example.naturelink.Repository.iClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ClothingService {
    @Autowired
    private iClothingRepository clothingRepository;

    @Autowired
    private IDestinationRepository destinationRepository;

    @Autowired
    private WeatherService weatherService;

    // Utilisation du même dossier uploads
    private final Path rootLocation = Paths.get("uploads");

    public List<Clothing> getAllClothingItems() {
        return clothingRepository.findAll();
    }

    public List<Clothing> getClothingsByDestination(Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId).orElse(null);

        if (destination == null) {
            return List.of();  // Retourner une liste vide si destination non trouvée
        }

        // Récupère la saison à partir de la ville de la destination
        String season = weatherService.getCurrentSeason(destination.getNom());

        // Récupère les plats par saison ET par destinationId
        return clothingRepository.findBySeasonAndDestinationId(season, destinationId);
    }

    public Clothing addClothing(Clothing clothing) {
        return clothingRepository.save(clothing);
    }

    public Clothing addClothingWithImage(String name, String description,
                                         String season, Long destinationId,
                                         MultipartFile image) {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Files.copy(image.getInputStream(), this.rootLocation.resolve(filename));
                imageUrl = "/uploads/" + filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Clothing clothing = new Clothing();
        clothing.setName(name);
        clothing.setDescription(description);
        clothing.setSeason(season);
        clothing.setImageUrl(imageUrl);
        clothing.setDestination(destination);

        return clothingRepository.save(clothing);
    }
    public void deleteClothing(Long id) {
        clothingRepository.deleteById(id);
    }
    public Clothing updateClothingWithImage(Long id, String name, String description,
                                            String season, Long destinationId,
                                            MultipartFile image) {
        return clothingRepository.findById(id)
                .map(existingClothing -> {
                    // Update basic fields
                    existingClothing.setName(name);
                    existingClothing.setDescription(description);
                    existingClothing.setSeason(season);

                    // Update destination if needed
                    if (destinationId != null) {
                        Destination destination = destinationRepository.findById(destinationId)
                                .orElseThrow(() -> new RuntimeException("Destination not found"));
                        existingClothing.setDestination(destination);
                    }

                    // Handle image update if provided
                    if (image != null && !image.isEmpty()) {
                        try {
                            // Delete old image if exists
                            if (existingClothing.getImageUrl() != null) {
                                String oldFilename = existingClothing.getImageUrl().replace("/uploads/", "");
                                Path oldFile = rootLocation.resolve(oldFilename);
                                Files.deleteIfExists(oldFile);
                            }

                            // Save new image
                            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                            Files.copy(image.getInputStream(), this.rootLocation.resolve(filename));
                            existingClothing.setImageUrl("/uploads/" + filename);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to update image: " + e.getMessage());
                        }
                    }

                    return clothingRepository.save(existingClothing);
                })
                .orElseThrow(() -> new RuntimeException("Clothing not found with id: " + id));
    }
    public Clothing getClothingById(Long id) {
        return clothingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clothing not found with id: " + id));
    }



    public Clothing updateClothing(Long id, String name, String description, String season,
                                   Long destinationId, MultipartFile file) {
        Clothing existingClothing = clothingRepository.findById(id).orElse(null);
        if (existingClothing == null) {
            return null;
        }

        Destination destination = destinationRepository.findById(destinationId).orElse(null);
        if (destination == null) {
            return null;
        }

        // Sauvegarder l'ancienne URL
        String oldImageUrl = existingClothing.getImageUrl();

        // Mettre à jour les champs
        existingClothing.setName(name);
        existingClothing.setDescription(description);
        existingClothing.setSeason(season);
        existingClothing.setDestination(destination);

        // Gestion de l'image

        if (file != null && !file.isEmpty()) {
            try {
                // Créer le répertoire s'il n'existe pas
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                // Supprimer l'ancienne image si elle existe
                if (existingClothing.getImageUrl() != null) {
                    Path oldImagePath = Paths.get("." + existingClothing.getImageUrl());
                    Files.deleteIfExists(oldImagePath);
                }

                // Générer un nouveau nom de fichier unique
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path destinationFile = rootLocation.resolve(filename);

                // Copier le nouveau fichier
                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

                // Mettre à jour l'URL de l'image
                existingClothing.setImageUrl("/uploads/" + filename);
            } catch (IOException e) {
                existingClothing.setImageUrl(oldImageUrl);
                System.err.println("Failed to update image: " + e.getMessage());
            }
        }

        return clothingRepository.save(existingClothing);
    }
}