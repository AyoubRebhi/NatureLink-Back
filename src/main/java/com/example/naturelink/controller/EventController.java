package com.example.naturelink.controller;

import com.example.naturelink.entity.Event;
import com.example.naturelink.service.EventService;
import com.example.naturelink.service.ExportPDFService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EventController {
    private final EventService eventService;
    private final ExportPDFService exportPDFService;


    @GetMapping("/export/pdf")
    public ResponseEntity<InputStreamResource> exportAllEventsToPdf() {
        var events = eventService.getAllEvents();
        var pdf = exportPDFService.exportEventsToPdf(events);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=events.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
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
