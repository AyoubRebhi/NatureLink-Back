package com.example.naturelink.services;

import com.example.naturelink.entity.Event;
import com.example.naturelink.repository.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final IEventRepository eventRepository;

    // Constructor injection to initialize the repository
    @Autowired
    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


public Event createEvent(Event event) {
    return eventRepository.save(event);
}
public List<Event> getAllEvents() {
    return eventRepository.findAll();
}

public Event getEventById(int id) {
    return eventRepository.findById(Long.valueOf(id)).orElse(null);
}


public Event updateEvent(Event event) {
    return eventRepository.save(event);
}
public void deleteEventById(int id) {
    eventRepository.deleteById(Long.valueOf(id));
}
}