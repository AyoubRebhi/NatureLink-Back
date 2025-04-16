package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Transport;
import com.example.naturelink.Service.ITransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transport")
public class TransportController {
    @Autowired

    ITransportService transportService;

    @GetMapping
    public List<Transport> getAllTransports() {
        return transportService.getAllTransports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transport> getTransportById(@PathVariable Integer id) {
        Optional<Transport> transport = transportService.getTransportById(id);
        return transport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
}
