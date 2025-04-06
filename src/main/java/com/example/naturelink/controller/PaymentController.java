package com.example.naturelink.controller;

import com.example.naturelink.entity.Payment;
import com.example.naturelink.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment, Authentication authentication) {
        return ResponseEntity.ok(
                paymentService.createPayment(payment, authentication.getName())
        );
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getUserPayments(Authentication authentication) {
        return ResponseEntity.ok(
                paymentService.getUserPayments(authentication.getName())
        );
    }
}