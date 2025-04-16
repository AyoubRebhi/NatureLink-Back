package com.example.naturelink.controllers;

import com.example.naturelink.dto.PackDTO;
import com.example.naturelink.entity.Pack;
import com.example.naturelink.Service.IPackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/packs")
@CrossOrigin(origins = "*") // Optional: for frontend testing
public class PackController {

    private final IPackService packService;
    public PackController(IPackService packService) {
        this.packService = packService;
    }
    // ➕ Add a new pack
    @PostMapping
    public ResponseEntity<?> addPack(@RequestBody PackDTO packDTO) {
        packService.addPack(packDTO);
        return ResponseEntity.ok().build(); // ✅ correct
    }


    // ✏️ Update an existing pack
    @PutMapping("/update/{id}")
    public ResponseEntity<Pack> updatePack(@PathVariable Long id, @RequestBody PackDTO dto) {
        try {
            Pack updatedPack = packService.updatePack(id, dto);
            return ResponseEntity.ok(updatedPack);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ❌ Delete a pack by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        try {
            packService.deletePack(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 📦 Get all packs
    @GetMapping
    public ResponseEntity<List<PackDTO>> getAllPacks() {
        List<PackDTO> packs = packService.getAllPacks();
        return ResponseEntity.ok(packs); // ✅ No casting needed
    }


    // 🔍 Get pack by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pack> getPackById(@PathVariable Long id) {
        Optional<Pack> pack = packService.getPackById(id);
        return pack.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
