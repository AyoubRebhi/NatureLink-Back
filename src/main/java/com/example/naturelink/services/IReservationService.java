package com.example.naturelink.services;

import com.example.naturelink.entity.Reservation;
import com.example.naturelink.entity.TypeReservation;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> getAllReservations();
    Optional<Reservation> getReservationById(Long id);
    Reservation addReservation(Reservation reservation);
    Reservation updateReservation(Long id, Reservation reservation);
    void deleteReservation(Long id);
    List<Reservation> getReservationsByType(TypeReservation typeres);
    List<Reservation> getReservationsByTypeAndLogement(TypeReservation typeres);
    List<Reservation> getReservationsByTypeAndEvent(TypeReservation typeres);
    List<Reservation> getReservationsByTypeAndRestaurant(TypeReservation typeres);
    List<Reservation> getReservationsByTypeAndTransport(TypeReservation typeres);
    Optional<Reservation> getReservationByTypeAndId(TypeReservation typeres, Long id);
    Reservation addReservationByType(TypeReservation typeres, Reservation reservation);
    Reservation updateReservationByType(TypeReservation typeres, Long id, Reservation reservation);
    void deleteReservationByType(TypeReservation typeres, Long id);
}
