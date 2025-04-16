package com.example.naturelink.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.naturelink.Entity.Transport;
import com.example.naturelink.Repository.ITransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransportService implements ITransportService{
    private final ITransportRepository transportRepository;
    @Autowired
    private Cloudinary cloudinary;

    public TransportService(ITransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }


    @Override
    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    @Override
    public Optional<Transport> getTransportById(Integer id) {
        return transportRepository.findById(Long.valueOf(id));
    }

    @Override
    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    @Override
    public Transport updateTransport(Integer id, Transport transportDetails) {
        return transportRepository.findById(Long.valueOf(id)).map(transport -> {
            transport.setType(transportDetails.getType());
            transport.setCapacity(transportDetails.getCapacity());
            transport.setPricePerKm(transportDetails.getPricePerKm());
            transport.setAvailable(transportDetails.getAvailable());
            transport.setDescription(transportDetails.getDescription());
            return transportRepository.save(transport);
        }).orElseThrow(() -> new RuntimeException("Transport not found"));
    }

    @Override
    public void deleteTransport(Integer id) {
        transportRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public Transport addTransportWithImage(Transport transport, MultipartFile imageFile) {
        try {
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            transport.setImgUrl((String) uploadResult.get("secure_url"));
            return transportRepository.save(transport);
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
    @Override
    public Transport updateTransportWithImage(Integer id, Transport transportDetails, MultipartFile imageFile) {
        return transportRepository.findById(Long.valueOf(id)).map(transport -> {
            transport.setType(transportDetails.getType());
            transport.setCapacity(transportDetails.getCapacity());
            transport.setPricePerKm(transportDetails.getPricePerKm());
            transport.setAvailable(transportDetails.getAvailable());
            transport.setDescription(transportDetails.getDescription());

            try {
                if (imageFile != null && !imageFile.isEmpty()) {
                    Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
                    transport.setImgUrl((String) uploadResult.get("secure_url"));
                }
            } catch (Exception e) {
                throw new RuntimeException("Image upload failed", e);
            }

            return transportRepository.save(transport);
        }).orElseThrow(() -> new RuntimeException("Transport not found"));
    }

}
