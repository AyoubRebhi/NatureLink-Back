package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Boutique;
import com.example.naturelink.Entity.Produit;
import com.example.naturelink.Service.BoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/boutiques")
public class BoutiqueController {
    @Autowired
    private BoutiqueService boutiqueService;

    @PostMapping
    public Boutique createBoutique(@RequestBody Boutique boutique) {
        return boutiqueService.createBoutique(boutique);
    }

    @PostMapping("/{id}/produits")
    public Produit addProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return boutiqueService.addProduitToBoutique(id, produit);
    }

    @GetMapping("/{id}/produits")
    public List<Produit> getProduits(@PathVariable Long id) {
        return boutiqueService.getProduitsByBoutique(id);
    }

    @GetMapping
    public List<Boutique> getAll() {
        return boutiqueService.getAllBoutiques();
    }

    @GetMapping("/{id}")
    public Optional<Boutique> getBoutiqueById(@PathVariable Long id) {
        return boutiqueService.getById(id);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateBoutique(@PathVariable Long id, @RequestBody Boutique boutique) {
        Optional<Boutique> updatedBoutique = boutiqueService.getById(id);
        if (updatedBoutique.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        updatedBoutique.get().setNom(boutique.getNom());
        updatedBoutique.get().setEmail(boutique.getEmail());
        updatedBoutique.get().setAdresse(boutique.getAdresse());
        updatedBoutique.get().setTelephone(boutique.getTelephone());
        updatedBoutique.get().setImage(boutique.getImage());

        Boutique boutique1 = boutiqueService.updateBoutique(updatedBoutique.orElse(null));
        return ResponseEntity.ok(boutique1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boutique> deleteBoutique(@PathVariable Long id) {
        Optional<Boutique> boutique = boutiqueService.getById(id);
        if (boutique.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boutiqueService.deleteBoutiqueById(id);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{boutiqueId}/produits/{productId}")
    public ResponseEntity<Void> deleteProduitByBoutiqueId(@PathVariable Long boutiqueId, @PathVariable Long productId) {
        boolean deleted = boutiqueService.deleteProduitByBoutiqueId(boutiqueId, productId);
        if (deleted) {
            return ResponseEntity.noContent().build(); // No content, successful deletion
        } else {
            return ResponseEntity.notFound().build(); // Product not found
        }
    }

}
