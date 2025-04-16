package com.example.naturelink.controller;

import com.example.naturelink.dto.ReservationDTO;
import com.example.naturelink.entity.Reservation;
import com.example.naturelink.entity.TypeReservation;
import com.example.naturelink.service.ExportPDFService;
import com.example.naturelink.service.ReservationService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;  // Add this import
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")  // Allow CORS for this controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    ExportPDFService exportPDFService;

    // Get all reservations
    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Get a reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        Optional<ReservationDTO> reservationDTO = reservationService.getReservationById(id);
        return reservationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new reservation
    @PostMapping
    public ResponseEntity<Map<String, String>> createReservation(@RequestBody ReservationDTO reservationDTO) {
        // Validate required fields: userId, start date, and end date
        if (reservationDTO.getUserId() == null ||
                reservationDTO.getDateDebut() == null ||
                reservationDTO.getDateFin() == null ||
                reservationDTO.getNumClients() == null ||
                reservationDTO.getClientNames() == null || reservationDTO.getClientNames().isEmpty()) {

            return ResponseEntity.badRequest().body(Map.of("error", "User ID, start date, end date, numClients, and clientNames are required."));
        }

        // Handle reservation for logement only if logementId is provided
        if (reservationDTO.getLogementId() != null) {
            reservationService.addReservationByType(TypeReservation.LOGEMENT, reservationDTO);
        }
        // Handle reservation for event only if eventId is provided
        else if (reservationDTO.getEventId() != null) {
            reservationService.addReservationByType(TypeReservation.EVENT, reservationDTO);
        }
        // Handle reservation for restaurant only if restaurantId is provided
        else if (reservationDTO.getRestaurantId() != null) {
            reservationService.addReservationByType(TypeReservation.RESTAURANT, reservationDTO);
        }
        // Handle reservation for transport only if transportId is provided
        else if (reservationDTO.getTransportId() != null) {
            reservationService.addReservationByType(TypeReservation.TRANSPORT, reservationDTO);
        }
        // Handle reservation for activity only if activityId is provided
        else if (reservationDTO.getActivityId() != null) {
            reservationService.addReservationByType(TypeReservation.ACTIVITE, reservationDTO);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "At least one entity (logement, event, etc.) must be provided for the reservation."));
        }

        // Process reservation, save, etc.
        return ResponseEntity.ok(Map.of("message", "Reservation created successfully!"));
    }

    // Update an existing reservation
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id,
                                                            @RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO updatedReservation = reservationService.updateReservation(id, reservationDTO);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a reservation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get reservations by type
    @GetMapping("/type/{typeres}")
    public List<ReservationDTO> getReservationsByType(@PathVariable TypeReservation typeres) {
        return reservationService.getReservationsByType(typeres);
    }

    // Get reservations by type and logement
    @GetMapping("/type/{typeres}/logement")
    public List<ReservationDTO> getReservationsByTypeAndLogement(@PathVariable TypeReservation typeres) {
        return reservationService.getReservationsByTypeAndLogement(typeres);
    }

    // Get reservations by type and event
    @GetMapping("/type/{typeres}/event")
    public List<ReservationDTO> getReservationsByTypeAndEvent(@PathVariable TypeReservation typeres) {
        return reservationService.getReservationsByTypeAndEvent(typeres);
    }

    // Get reservations by type and restaurant
    @GetMapping("/type/{typeres}/restaurant")
    public List<ReservationDTO> getReservationsByTypeAndRestaurant(@PathVariable TypeReservation typeres) {
        return reservationService.getReservationsByTypeAndRestaurant(typeres);
    }

    // Get reservations by type and transport
    @GetMapping("/type/{typeres}/transport")
    public List<ReservationDTO> getReservationsByTypeAndTransport(@PathVariable TypeReservation typeres) {
        return reservationService.getReservationsByTypeAndTransport(typeres);
    }

    // Get reservation by type and ID
    @GetMapping("/type/{typeres}/{id}")
    public ResponseEntity<ReservationDTO> getReservationByTypeAndId(@PathVariable TypeReservation typeres,
                                                                    @PathVariable Long id) {
        Optional<ReservationDTO> reservationDTO = reservationService.getReservationByTypeAndId(typeres, id);
        return reservationDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a reservation by type
    @PostMapping("/type/{typeres}")
    public ReservationDTO addReservationByType(@PathVariable TypeReservation typeres,
                                               @RequestBody ReservationDTO reservationDTO) {
        return reservationService.addReservationByType(typeres, reservationDTO);
    }

    // Update a reservation by type
    @PutMapping("/type/{typeres}/{id}")
    public ResponseEntity<ReservationDTO> updateReservationByType(@PathVariable TypeReservation typeres,
                                                                  @PathVariable Long id,
                                                                  @RequestBody ReservationDTO reservationDTO) {
        try {
            ReservationDTO updatedReservation = reservationService.updateReservationByType(typeres, id, reservationDTO);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a reservation by type
    @DeleteMapping("/type/{typeres}/{id}")
    public ResponseEntity<Void> deleteReservationByType(@PathVariable TypeReservation typeres,
                                                        @PathVariable Long id) {
        try {
            reservationService.deleteReservationByType(typeres, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByUserIdDTO(userId);
        if (reservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservations);
    }

    // Endpoint to generate and download reservation PDF
    @GetMapping("/api/reservations/{id}/pdf")
    public ResponseEntity<byte[]> downloadReservationPDF(@PathVariable("id") Long id) {
        Optional<ReservationDTO> reservationOpt = reservationService.getReservationById(id); // Get the Reservation entity

        if (!reservationOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Return 404 if the reservation is not found
        }

        ReservationDTO reservation = reservationOpt.get();  // Get the Reservation entity

        // Now pass both the ID and the Reservation entity to generateReservationPDF


        ByteArrayInputStream bis = exportPDFService.generateReservationPDF(id, reservation);

        byte[] pdfContent = bis.readAllBytes();  // Convert the input stream to byte array

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reservation_" + id + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);  // Return the PDF with appropriate headers
    }

}



