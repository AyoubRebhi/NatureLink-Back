package com.example.naturelink.repository;

import com.example.naturelink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Define custom query methods if needed
}
