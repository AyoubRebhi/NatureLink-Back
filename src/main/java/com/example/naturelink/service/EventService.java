package com.example.naturelink.service;

import com.example.naturelink.entity.Event;
import com.example.naturelink.repository.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final IEventRepository eventRepository;

public Event createEvent(Event event) {
    return eventRepository.save(event);
}
public List<Event> getAllEvents() {
    return eventRepository.findAll();
}

public Event getEventById(int id) {
    return eventRepository.findById(id).orElse(null);
}


public Event updateEvent(Event event) {
    return eventRepository.save(event);
}
public void deleteEventById(int id) {
    eventRepository.deleteById(id);
}
}
