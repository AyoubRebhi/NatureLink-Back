package com.example.naturelink.service;

import com.example.naturelink.entity.Restaurant;

import java.util.List;
import java.util.Optional;
public interface IRestaurantService {

    List<Restaurant> getAllRestaurants();

    Optional<Restaurant> getRestaurantById(Long id);

    Restaurant addRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Long id, Restaurant restaurant);

    void deleteRestaurant(Long id);
}
