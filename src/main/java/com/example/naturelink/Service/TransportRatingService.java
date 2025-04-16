package com.example.naturelink.Service;

import com.example.naturelink.Entity.Transport;
import com.example.naturelink.Entity.TransportRating;
import com.example.naturelink.Repository.ITransportRating;
import com.example.naturelink.Repository.ITransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
public class TransportRatingService implements ITransportRatingService {

    @Autowired
    private ITransportRating transportRatingRepository;
    @Autowired
    private ITransportRepository transportRepository;

    @Override
    public TransportRating addRating(TransportRating rating) {
        Transport transport = transportRepository.findById(Long.valueOf(rating.getTransportId()))
                .orElseThrow(() -> new RuntimeException("Transport not found"));

        rating.setTransport(transport);            // Set the actual transport Entity
        rating.setTransportId(transport.getId());  // Also ensure transportId is set explicitly (optional)

        return transportRatingRepository.save(rating);
    }


    @Override
    public List<TransportRating> getAllRatings() {
        return transportRatingRepository.findAll();
    }

    @Override
    public Optional<TransportRating> getRatingById(Integer id) {
        return transportRatingRepository.findById(id);
    }

    @Override
    public void deleteRating(Integer id) {
        transportRatingRepository.deleteById(id);
    }

    public List<TransportRating> getRatingsByTransportId(Integer transportId) {
        return transportRatingRepository.findAll().stream()
                .filter(rating -> rating.getTransportId() != null && rating.getTransportId().equals(transportId))
                .toList();
    }


    @Override
    public Double getAverageRatingForTransport(Integer transportId) {
        List<TransportRating> ratings = getRatingsByTransportId(transportId);
        return ratings.isEmpty() ? 0.0 :
                ratings.stream()
                        .flatMapToDouble(r -> DoubleStream.of(r.getRating()))
                        .average()
                        .orElse(0.0);
    }
}
