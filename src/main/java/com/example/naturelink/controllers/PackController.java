package com.example.naturelink.controllers;

import com.example.naturelink.entity.Pack;
import com.example.naturelink.services.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/packs")
public class PackController {

    @Autowired
    private PackService packService;

    @GetMapping
    public List<Pack> getAllPacks() {
        return packService.getAllPacks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pack> getPackById(@PathVariable Long id) {
        Optional<Pack> pack = packService.getPackById(id);
        return pack.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pack addPack(@RequestBody Pack pack) {
        return packService.addPack(pack);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pack> updatePack(@PathVariable Long id, @RequestBody Pack pack) {
        try {
            return ResponseEntity.ok(packService.updatePack(id, pack));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        try {
            packService.deletePack(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
