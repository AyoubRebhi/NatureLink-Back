package com.example.naturelink.repository;

import com.example.naturelink.entity.Reservation;
import com.example.naturelink.entity.TypeReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Custom methods to find reservations by TypeReservation
    List<Reservation> findByTyperes(TypeReservation typeres);

    // Custom method to find reservations by TypeReservation and Logement
    List<Reservation> findByTyperesAndLogementIdIsNotNull(TypeReservation typeres);

    // Custom method to find reservations by TypeReservation and Event
    List<Reservation> findByTyperesAndEventIdIsNotNull(TypeReservation typeres);

    // Custom method to find reservations by TypeReservation and Restaurant
    List<Reservation> findByTyperesAndRestaurantIdIsNotNull(TypeReservation typeres);

    // Custom method to find reservations by TypeReservation and Transport
    List<Reservation> findByTyperesAndTranspIdIsNotNull(TypeReservation typeres);

    // Custom method to check if a reservation exists by TypeReservation and ID
    boolean existsByTyperesAndId(TypeReservation typeres, Long id);

    // Custom method to find reservation by TypeReservation and ID
    Optional<Reservation> findByTyperesAndId(TypeReservation typeres, Long id);
}
