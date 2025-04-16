package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Food;
import com.example.naturelink.Service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/{id}")
    public Food getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @GetMapping("/byDestination/{destinationId}")
    public List<Food> getFoodsByDestination(@PathVariable Long destinationId) {
        return foodService.getFoodsByDestination(destinationId);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Food> addFoodWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("season") String season,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Food food = foodService.addFoodWithImage(name, description, season, destinationId, image);
        return ResponseEntity.ok(food);
    }


    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Food> updateFood(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("season") String season,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Food updatedFood = foodService.updateFood(id, nom, description, season, destinationId, file);
        return ResponseEntity.ok(updatedFood);
    }
}
