package com.example.naturelink.service;

import com.example.naturelink.dto.ReservationDTO;
import com.example.naturelink.entity.*;

import java.util.List;
import java.util.Optional;

public interface IReservationService {

    // Get all reservations as DTOs
    List<ReservationDTO> getAllReservations();

    // Get a reservation by ID as a DTO
    Optional<ReservationDTO> getReservationById(Long id);

    // Add a new reservation
    ReservationDTO addReservation(ReservationDTO reservationDTO);

    // Update an existing reservation by ID
    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);

    // Delete a reservation by ID
    void deleteReservation(Long id);

    // Get reservations by type as DTOs
    List<ReservationDTO> getReservationsByType(TypeReservation typeres);

    // Get reservations by type and logement
    List<ReservationDTO> getReservationsByTypeAndLogement(TypeReservation typeres);

    // Get reservations by type and event
    List<ReservationDTO> getReservationsByTypeAndEvent(TypeReservation typeres);

    // Get reservations by type and restaurant
    List<ReservationDTO> getReservationsByTypeAndRestaurant(TypeReservation typeres);

    // Get reservations by type and transport
    List<ReservationDTO> getReservationsByTypeAndTransport(TypeReservation typeres);

    // Get reservation by type and ID
    Optional<ReservationDTO> getReservationByTypeAndId(TypeReservation typeres, Long id);

    // Add a reservation by type
    ReservationDTO addReservationByType(TypeReservation typeres, ReservationDTO reservationDTO);

    // Update a reservation by type and ID
    ReservationDTO updateReservationByType(TypeReservation typeres, Long id, ReservationDTO reservationDTO);

    // Delete a reservation by type and ID
    void deleteReservationByType(TypeReservation typeres, Long id);
    List<ReservationDTO> getReservationsByUserIdDTO(Long userId);

}
