package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByIdAndBoutiqueId(Long productId, Long boutiqueId);

}
