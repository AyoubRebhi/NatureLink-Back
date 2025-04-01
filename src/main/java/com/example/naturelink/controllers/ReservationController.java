package com.example.naturelink.controllers;

import com.example.naturelink.entity.Reservation;
import com.example.naturelink.entity.TypeReservation;
import com.example.naturelink.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            return ResponseEntity.ok(reservationService.updateReservation(id, reservation));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type/{typeres}")
    public List<Reservation> getReservationsByType(@PathVariable TypeReservation typeres) {
        return reservationService.getReservationsByType(typeres);
    }

    @GetMapping("/type/{typeres}/{id}")
    public ResponseEntity<Reservation> getReservationByTypeAndId(@PathVariable TypeReservation typeres, @PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationByTypeAndId(typeres, id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/type/{typeres}")
    public Reservation addReservationByType(@PathVariable TypeReservation typeres, @RequestBody Reservation reservation) {
        // Handle relation with User
        reservation.setUserId(reservation.getUser().getId());
        return reservationService.addReservationByType(typeres, reservation);
    }

    @PutMapping("/type/{typeres}/{id}")
    public ResponseEntity<Reservation> updateReservationByType(@PathVariable TypeReservation typeres, @PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            // Handle relation with User
            reservation.setUserId(reservation.getUser().getId());
            return ResponseEntity.ok(reservationService.updateReservationByType(typeres, id, reservation));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/type/{typeres}/{id}")
    public ResponseEntity<Void> deleteReservationByType(@PathVariable TypeReservation typeres, @PathVariable Long id) {
        try {
            reservationService.deleteReservationByType(typeres, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
