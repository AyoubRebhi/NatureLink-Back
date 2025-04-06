package com.example.naturelink.repository;

import com.example.naturelink.entity.Payment;
import com.example.naturelink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);
}