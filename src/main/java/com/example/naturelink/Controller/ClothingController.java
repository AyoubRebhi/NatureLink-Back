package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Clothing;
import com.example.naturelink.Service.ClothingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/clothing")
@RequiredArgsConstructor
public class ClothingController {
    private final ClothingService clothingService;

    @GetMapping
    public List<Clothing> getAllClothingItems() {
        return clothingService.getAllClothingItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clothing> getClothingById(@PathVariable Long id) {
        Clothing clothing = clothingService.getClothingById(id);
        return ResponseEntity.ok(clothing);
    }

    @GetMapping("/destination/{destinationId}")
    public List<Clothing> getClothingByDestination(@PathVariable Long destinationId) {
        return clothingService.getClothingsByDestination(destinationId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Clothing> addClothingWithImage(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("season") String season,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Clothing clothing = clothingService.addClothingWithImage(name, description, season, destinationId, image);
        return ResponseEntity.ok(clothing);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Clothing> updateClothing(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("season") String season,
            @RequestParam("destinationId") Long destinationId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Clothing updatedClothing = clothingService.updateClothing(id, name, description, season, destinationId, file);
        return ResponseEntity.ok(updatedClothing);
    }

    @DeleteMapping("/{id}")
    public void deleteClothing(@PathVariable Long id) {
        clothingService.deleteClothing(id);
    }
}