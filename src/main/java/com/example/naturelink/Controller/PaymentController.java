package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Payment;
import com.example.naturelink.Service.PaymentService;
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
    @GetMapping("/pending")
    public ResponseEntity<List<Payment>> getPendingPayments(Authentication authentication) {
        return ResponseEntity.ok(
                paymentService.getPendingPayments(authentication.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                paymentService.updatePayment(id, payment, authentication.getName())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long id,
            Authentication authentication
    ) {
        paymentService.deletePayment(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
    }

}