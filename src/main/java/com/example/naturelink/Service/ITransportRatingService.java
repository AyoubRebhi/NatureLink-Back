package com.example.naturelink.Service;

import com.example.naturelink.Entity.TransportRating;

import java.util.List;
import java.util.Optional;

public interface ITransportRatingService {

    TransportRating addRating(TransportRating rating);

    List<TransportRating> getAllRatings();

    List<TransportRating> getRatingsByTransportId(Integer transportId);

    Optional<TransportRating> getRatingById(Integer id);

    void deleteRating(Integer id);

    Double getAverageRatingForTransport(Integer transportId);
}
