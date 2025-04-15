package com.example.naturelink.services;

import com.example.naturelink.dto.ReservationDTO;
import com.example.naturelink.entity.Reservation;
import com.example.naturelink.repository.ReservationRepository;
import com.example.naturelink.entity.*;
import com.example.naturelink.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ILogementRepository logementRepository;

    @Autowired
    private IEventRepository evenementRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ITransportRepository transportRepository;

    @Autowired
    private IActivityRepository activityRepository;

    // Method to convert from ReservationDTO to Reservation entity
    private Reservation convertToEntity(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setUserId(Math.toIntExact(dto.getUserId()));
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());
        reservation.setId(reservation.getId()); // Don't forget this

        // Set the number of clients and usernames
        reservation.setNumClients(dto.getNumClients());
        reservation.setClientNames(dto.getClientNames());

        if (dto.getLogementId() != null) {
            Logement logement = logementRepository.findById(dto.getLogementId())
                    .orElseThrow(() -> new RuntimeException("Logement not found"));
            reservation.setLogementId(logement);
            reservation.setTyperes(TypeReservation.LOGEMENT);
            reservation.setNumRooms(dto.getNumRooms()); // Set number of rooms if the reservation is for logement
        } else if (dto.getEventId() != null) {
            Event event = evenementRepository.findById(dto.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            reservation.setEventId(event);
            reservation.setTyperes(TypeReservation.EVENT);
        } else if (dto.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            reservation.setRestaurant(restaurant);
            reservation.setTyperes(TypeReservation.RESTAURANT);
        } else if (dto.getTransportId() != null) {
            Transport transport = transportRepository.findById(Long.valueOf(dto.getTransportId()))
                    .orElseThrow(() -> new RuntimeException("Transport not found"));
            reservation.setTranspId(transport);
            reservation.setTyperes(TypeReservation.TRANSPORT);
        } else if (dto.getActivityId() != null) {
            Activity activity = activityRepository.findById(Long.valueOf(dto.getActivityId()))
                    .orElseThrow(() -> new RuntimeException("Activity not found"));
            reservation.setActivityId(activity);
            reservation.setTyperes(TypeReservation.ACTIVITE);
        } else {
            throw new RuntimeException("A reservation type must be specified.");
        }

        return reservation;
    }

    // Method to convert from Reservation entity to ReservationDTO
    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                Long.valueOf(reservation.getClient().getId()),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getLogementId() != null ? Long.valueOf(reservation.getLogementId().getId()) : null,
                reservation.getEventId() != null ? Long.valueOf(reservation.getEventId().getId()) : null,
                reservation.getRestaurant() != null ? reservation.getRestaurant().getId() : null,
                reservation.getTranspId() != null ? reservation.getTranspId().getId() : null,
                reservation.getActivityId() != null ? reservation.getActivityId().getId() : null,
                reservation.getStatut(),
                reservation.getId(),
                reservation.getNumClients(),  // Add number of clients
                reservation.getNumRooms(),    // Add number of rooms (if LOGEMENT)
                reservation.getClientNames()  // Add list of usernames
        );
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.map(this::convertToDTO);
    }

    @Override
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        // Handle reservation creation with validation based on the number of clients
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        if (reservationRepository.existsById(id)) {
            Reservation reservation = convertToEntity(reservationDTO);
            reservation.setId(id);
            Reservation updatedReservation = reservationRepository.save(reservation);
            return convertToDTO(updatedReservation);
        }
        throw new RuntimeException("Reservation not found");
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<ReservationDTO> getReservationsByType(TypeReservation typeres) {
        List<Reservation> reservations = reservationRepository.findByTyperes(typeres);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByTypeAndLogement(TypeReservation typeres) {
        List<Reservation> reservations = reservationRepository.findByTyperesAndLogementIdIsNotNull(typeres);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByTypeAndEvent(TypeReservation typeres) {
        List<Reservation> reservations = reservationRepository.findByTyperesAndEventIdIsNotNull(typeres);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByTypeAndRestaurant(TypeReservation typeres) {
        List<Reservation> reservations = reservationRepository.findByTyperesAndRestaurantIdIsNotNull(typeres);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getReservationsByTypeAndTransport(TypeReservation typeres) {
        List<Reservation> reservations = reservationRepository.findByTyperesAndTranspIdIsNotNull(typeres);
        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationDTO> getReservationByTypeAndId(TypeReservation typeres, Long id) {
        Optional<Reservation> reservation = reservationRepository.findByTyperesAndId(typeres, id);
        return reservation.map(this::convertToDTO);
    }

    @Override
    public ReservationDTO addReservationByType(TypeReservation typeres, ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        reservation.setTyperes(typeres);
        // Set userId
        reservation.setUserId(Math.toIntExact(reservationDTO.getUserId()));
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    @Override
    public ReservationDTO updateReservationByType(TypeReservation typeres, Long id, ReservationDTO reservationDTO) {
        if (reservationRepository.existsByTyperesAndId(typeres, id)) {
            Reservation reservation = convertToEntity(reservationDTO);
            reservation.setId(id);
            reservation.setTyperes(typeres);
            Reservation updatedReservation = reservationRepository.save(reservation);
            return convertToDTO(updatedReservation);
        }
        throw new RuntimeException("Reservation not found");
    }

    @Override
    public void deleteReservationByType(TypeReservation typeres, Long id) {
        if (reservationRepository.existsByTyperesAndId(typeres, id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    public List<ReservationDTO> getReservationsByUserIdDTO(Long userId) {
        List<Reservation> reservations = reservationRepository.findByClient_Id(userId); // Fetch reservations by user ID
        return reservations.stream()
                .map(this::convertToDTO)  // Convert each Reservation entity to ReservationDTO
                .collect(Collectors.toList());
    }
}
