package com.example.naturelink.controller;

import com.example.naturelink.entity.Transport;
import com.example.naturelink.service.ITransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/transport")
public class TransportController {
    @Autowired
    ITransportService transportService;

    @GetMapping
    public ResponseEntity<List<Transport>> getAllTransports() {
        List<Transport> transports = transportService.getAllTransports();
        return ResponseEntity.ok(transports); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTransportById(@PathVariable Integer id) {
        Optional<Transport> transport = transportService.getTransportById(id);
        return transport.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((Transport) Map.of("message", "Transport not found"))); // Fix: Use Map for JSON response
    }


    @PostMapping("/add")
    public Transport addTransport(@RequestBody Transport transport) {
        return transportService.addTransport(transport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transport> updateTransport(@PathVariable Integer id, @RequestBody Transport transport) {
        try {
            Transport updatedTransport = transportService.updateTransport(id, transport);
            return ResponseEntity.ok(updatedTransport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransport(@PathVariable Integer id) {
        transportService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/add-image", consumes = {"multipart/form-data"})
    public ResponseEntity<Transport> addTransportWithImage(
            @RequestPart("transport") Transport transport,
            @RequestPart("image") MultipartFile imageFile) {
        return ResponseEntity.ok(transportService.addTransportWithImage(transport, imageFile));
    }

    @PutMapping(value = "/update-image/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Transport> updateTransportWithImage(
            @PathVariable Integer id,
            @RequestPart("transport") Transport transport,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            Transport updated = transportService.updateTransportWithImage(id, transport, imageFile);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
