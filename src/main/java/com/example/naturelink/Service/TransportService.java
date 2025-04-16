package com.example.naturelink.Service;

import com.example.naturelink.Entity.Transport;
import com.example.naturelink.Repository.ITransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportService implements ITransportService{
    private final ITransportRepository transportRepository;

    public TransportService(ITransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }


    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    @Override
    public Optional<Transport> getTransportById(Integer id) {
        return transportRepository.findById(id);
    }

    @Override
    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public Transport updateTransport(Integer id, Transport transportDetails) {
        return transportRepository.findById(id).map(transport -> {
            transport.setType(transportDetails.getType());
            transport.setCapacity(transportDetails.getCapacity());
            transport.setPricePerKm(transportDetails.getPricePerKm());
            transport.setAvailable(transportDetails.getAvailable());
            return transportRepository.save(transport);
        }).orElseThrow(() -> new RuntimeException("Transport not found"));
    }

    @Override
    public void deleteTransport(Integer id) {
        transportRepository.deleteById(id);
    }
}
