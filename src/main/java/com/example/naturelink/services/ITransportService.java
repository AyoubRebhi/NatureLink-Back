package com.example.naturelink.services;

import com.example.naturelink.entity.Transport;

import java.util.List;
import java.util.Optional;

public interface ITransportService {

    List<Transport> getAllTransports();

    Optional<Transport> getTransportById(Integer id);

    Transport addTransport(Transport transport);

    Transport updateTransport(Integer id, Transport transport);

    void deleteTransport(Integer id);
}
