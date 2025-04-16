package com.example.naturelink.Repository;

import com.example.naturelink.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Define custom query methods if needed
}
