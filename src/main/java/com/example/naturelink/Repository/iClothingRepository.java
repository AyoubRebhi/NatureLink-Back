package com.example.naturelink.Repository;
import com.example.naturelink.Entity.Clothing;
import com.example.naturelink.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface iClothingRepository extends JpaRepository<Clothing, Long> {
    List<Clothing> findByDestinationId(Long destinationId);

    List<Clothing> findBySeason(String season);
    List<Clothing> findBySeasonAndDestinationId(String season, Long destinationId);

}

