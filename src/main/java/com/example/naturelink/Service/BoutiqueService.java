package com.example.naturelink.Service;

import com.example.naturelink.Entity.Boutique;
import com.example.naturelink.Entity.Produit;
import com.example.naturelink.Repository.IBoutiqueRepository;
import com.example.naturelink.Repository.IProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoutiqueService {
    @Autowired
    private IBoutiqueRepository boutiqueRepository;

    @Autowired
    private  IProduitRepository produitRepository;

    public Boutique createBoutique(Boutique boutique) {
        return boutiqueRepository.save(boutique);
    }

    public Produit addProduitToBoutique(Long boutiqueId, Produit produit) {
        Boutique boutique = boutiqueRepository.findById(boutiqueId)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));

        produit.setBoutique(boutique);
        return produitRepository.save(produit);
    }

    public List<Produit> getProduitsByBoutique(Long boutiqueId) {
        Boutique boutique = boutiqueRepository.findById(boutiqueId)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));

        return boutique.getProduits();
    }

    public List<Boutique> getAllBoutiques() {
        return boutiqueRepository.findAll();
    }


    public Optional<Boutique> getById(Long id) {

        return boutiqueRepository.findById(id);
    }
    public Boutique updateBoutique(Boutique boutique) {
        return boutiqueRepository.save(boutique);
    }
    public void deleteBoutiqueById(long id) {
        boutiqueRepository.deleteById(id);
    }


    public boolean deleteProduitByBoutiqueId(Long boutiqueId, Long productId) {
        // Find the boutique
        Boutique boutique = boutiqueRepository.findById(boutiqueId)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));

        // Find the product by boutique and product ID
        Optional<Produit> produit = produitRepository.findByIdAndBoutiqueId(productId, boutiqueId);

        if (produit.isPresent()) {
            produitRepository.delete(produit.get()); // Delete the product
            return true;
        } else {
            return false; // Return false if product was not found
        }
    }

}



