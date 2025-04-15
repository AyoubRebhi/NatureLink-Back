package com.example.naturelink.service;

import com.example.naturelink.entity.TransportRating;

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
