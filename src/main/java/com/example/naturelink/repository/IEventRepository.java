package com.example.naturelink.repository;

import com.example.naturelink.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventRepository extends JpaRepository<Event, Long> {
    // Define custom query methods if needed
}
