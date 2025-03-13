package com.example.naturelink.services;

import com.example.naturelink.entity.Transport;
import com.example.naturelink.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportService implements ITransportService {

    @Autowired
    private TransportRepository transportRepository;

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
    public Transport updateTransport(Integer id, Transport transport) {
        if (transportRepository.existsById(id)) {
            transport.setId(id);
            return transportRepository.save(transport);
        }
        throw new RuntimeException("Transport not found");
    }

    @Override
    public void deleteTransport(Integer id) {
        transportRepository.deleteById(id);
    }
}
