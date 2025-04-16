package com.example.naturelink.Service;

import com.example.naturelink.Entity.Transport;

import java.util.List;
import java.util.Optional;

public interface ITransportService {

    public List<Transport> getAllTransports();
    public Optional<Transport> getTransportById(Integer id);
    public Transport addTransport(Transport transport);
    public Transport updateTransport(Integer id, Transport transportDetails);
    public void deleteTransport(Integer id);
}
