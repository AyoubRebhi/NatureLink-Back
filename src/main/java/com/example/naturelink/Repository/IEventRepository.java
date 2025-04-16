package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {
    // Define custom query methods if needed
}
