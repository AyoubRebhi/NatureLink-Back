package com.example.naturelink.Service;

import com.example.naturelink.Entity.Destination;
import com.example.naturelink.Entity.Food;
import com.example.naturelink.Repository.FoodRepository;
import com.example.naturelink.Repository.IDestinationRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private IDestinationRepository DestinationRepository;

    @Autowired
    private WeatherService weatherService;
    @Autowired

    private IDestinationRepository destinationRepository;

    private final Path rootLocation = Paths.get("uploads");


    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public List<Food> getFoodsByDestination(Long destinationId) {
        // Récupère la destination
        Destination destination = destinationRepository.findById(destinationId).orElse(null);

        if (destination == null) {
            return List.of();  // Retourner une liste vide si destination non trouvée
        }

        // Récupère la saison à partir de la ville de la destination
        String season = weatherService.getCurrentSeason(destination.getNom());

        // Récupère les plats par saison ET par destinationId
        return foodRepository.findBySeasonAndDestinationId(season, destinationId);
    }



    public Food addFoodWithImage(String name, String description, String season,
                                 Long destinationId, MultipartFile image) {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Files.copy(image.getInputStream(), this.rootLocation.resolve(filename));
                imageUrl = "/uploads/" + filename;  // Même chemin que pour les posts
            } catch (IOException e) {
                // Gérer l'exception selon vos besoins
                e.printStackTrace();
            }
        }

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Food food = Food.builder()
                .nom(name)
                .description(description)
                .season(season)
                .imageUrl(imageUrl)
                .destination(destination)
                .build();

        return foodRepository.save(food);
    }


    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    public Food updateFood(Long id, String nom, String description, String season,
                           Long destinationId, MultipartFile file) {

        // 1. Trouver l'aliment existant
        Food existingFood = foodRepository.findById(id).orElse(null);
        if (existingFood == null) {
            return null;
        }

        // 2. Trouver la destination
        Destination destination = destinationRepository.findById(destinationId).orElse(null);
        if (destination == null) {
            return null;
        }

        // 3. Mettre à jour les champs de base
        existingFood.setNom(nom);
        existingFood.setDescription(description);
        existingFood.setSeason(season);
        existingFood.setDestination(destination);

        // 4. Gestion de l'image
        if (file != null && !file.isEmpty()) {
            try {
                // Créer le répertoire s'il n'existe pas
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                // Supprimer l'ancienne image si elle existe
                if (existingFood.getImageUrl() != null) {
                    Path oldImagePath = Paths.get("." + existingFood.getImageUrl());
                    Files.deleteIfExists(oldImagePath);
                }

                // Générer un nouveau nom de fichier unique
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path destinationFile = rootLocation.resolve(filename);

                // Copier le nouveau fichier
                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

                // Mettre à jour l'URL de l'image
                existingFood.setImageUrl("/uploads/" + filename);

            } catch (IOException e) {
                // Log l'erreur mais continuer sans mettre à jour l'image
                System.err.println("Erreur lors de la mise à jour de l'image: " + e.getMessage());
            }
        }

        // 5. Sauvegarder les modifications
        return foodRepository.save(existingFood);
    }

}
