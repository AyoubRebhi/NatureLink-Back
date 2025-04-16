package com.example.naturelink.controllers;

import com.example.naturelink.entity.Event;
import com.example.naturelink.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController {
    private final EventService eventService;
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/add")
    public Event createEvent(@RequestBody Event event) {return eventService.createEvent(event);
    }


    @GetMapping("/All")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/All/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable int id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }


    @PutMapping("/All/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable int id,@RequestBody Event event) {

        Event updatedEvent = eventService.getEventById(id);
    if (updatedEvent == null) {
    return ResponseEntity.notFound().build();
    }
    updatedEvent.setDate(event.getDate());
    updatedEvent.setDescription(event.getDescription());
    updatedEvent.setLocation(event.getLocation());
    updatedEvent.setFounder(event.getFounder());
    updatedEvent.setNbrplace(event.getNbrplace());
    updatedEvent.setTitle(event.getTitle());
    updatedEvent.setImage(event.getImage());
        Event event1 = eventService.updateEvent(updatedEvent);
return ResponseEntity.ok(event1);
    }



    @DeleteMapping("/All/delete/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable int id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }
}