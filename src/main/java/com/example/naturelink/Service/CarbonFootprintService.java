package com.example.naturelink.Service;

import com.example.naturelink.Entity.CarbonFootprint;
import com.example.naturelink.Repository.CarbonFootprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CarbonFootprintService {

    @Autowired
    private CarbonFootprintRepository repository;

    public CarbonFootprint saveFootprint(CarbonFootprint footprint) {
        return repository.save(footprint);
    }

    public List<CarbonFootprint> getFootprintsByTransport(String transportType) {
        return repository.findByTransportType(transportType);
    }

    public List<CarbonFootprint> getFootprintsByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    public List<CarbonFootprint> getFootprintsByUserAndTransport(Integer userId, String transportType) {
        return repository.findByUserIdAndTransportType(userId, transportType);
    }

    public List<CarbonFootprint> getAllFootprints() {
        return repository.findAll();
    }
}
