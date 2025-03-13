package com.example.naturelink.services;

import com.example.naturelink.entity.Reservation;
import com.example.naturelink.entity.TypeReservation;
import com.example.naturelink.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        if (reservationRepository.existsById(id)) {
            reservation.setId(id);
            return reservationRepository.save(reservation);
        }
        throw new RuntimeException("Reservation not found");
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByType(TypeReservation typeres) {
        return reservationRepository.findByTyperes(typeres);
    }

    @Override
    public List<Reservation> getReservationsByTypeAndLogement(TypeReservation typeres) {
        return reservationRepository.findByTyperesAndLogementIdIsNotNull(typeres);
    }

    @Override
    public List<Reservation> getReservationsByTypeAndEvent(TypeReservation typeres) {
        return reservationRepository.findByTyperesAndEventIdIsNotNull(typeres);
    }

    @Override
    public List<Reservation> getReservationsByTypeAndRestaurant(TypeReservation typeres) {
        return reservationRepository.findByTyperesAndRestaurantIdIsNotNull(typeres);
    }

    @Override
    public List<Reservation> getReservationsByTypeAndTransport(TypeReservation typeres) {
        return reservationRepository.findByTyperesAndTranspIdIsNotNull(typeres);
    }

    // CRUD functions for reservations depending on the type of reservation

    public Optional<Reservation> getReservationByTypeAndId(TypeReservation typeres, Long id) {
        return reservationRepository.findByTyperesAndId(typeres, id);
    }

    public Reservation addReservationByType(TypeReservation typeres, Reservation reservation) {
        reservation.setTyperes(typeres);
        // Handle relations based on the type of reservation
        if (typeres == TypeReservation.LOGEMENT) {
            reservation.setLogementId(reservation.getLogementId());
        } else if (typeres == TypeReservation.ACTIVITE) {
            reservation.setActiviteId(reservation.getActiviteId());
        } else if (typeres == TypeReservation.EVENT) {
            reservation.setEventId(reservation.getEventId());
        } else if (typeres == TypeReservation.RESTAURANT) {
            reservation.setRestaurantId(reservation.getRestaurantId());
        } else if (typeres == TypeReservation.TRANSPORT) {
            reservation.setTranspId(reservation.getTranspId());
        }
        // Set user ID
        reservation.setUserId(reservation.getClient().getId());
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservationByType(TypeReservation typeres, Long id, Reservation reservation) {
        if (reservationRepository.existsByTyperesAndId(typeres, id)) {
            reservation.setId(id);
            reservation.setTyperes(typeres);
            // Handle relations based on the type of reservation
            if (typeres == TypeReservation.LOGEMENT) {
                reservation.setLogementId(reservation.getLogementId());
            } else if (typeres == TypeReservation.ACTIVITE) {
                reservation.setActiviteId(reservation.getActiviteId());
            } else if (typeres == TypeReservation.EVENT) {
                reservation.setEventId(reservation.getEventId());
            } else if (typeres == TypeReservation.RESTAURANT) {
                reservation.setRestaurantId(reservation.getRestaurantId());
            } else if (typeres == TypeReservation.TRANSPORT) {
                reservation.setTranspId(reservation.getTranspId());
            }
            // Set user ID
            reservation.setUserId(reservation.getClient().getId());
            return reservationRepository.save(reservation);
        }
        throw new RuntimeException("Reservation not found");
    }

    public void deleteReservationByType(TypeReservation typeres, Long id) {
        if (reservationRepository.existsByTyperesAndId(typeres, id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }
}
