package com.example.naturelink.Service;

import com.example.naturelink.Entity.Payment;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Repository.PaymentRepository;
import com.example.naturelink.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public Payment createPayment(Payment payment, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        payment.setUser(user);
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public List<Payment> getUserPayments(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return paymentRepository.findByUser(user);
    }
    public Payment updatePayment(Long paymentId, Payment updatedPayment, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Payment existing = paymentRepository.findByIdAndUser(paymentId, user)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existing.setAmount(updatedPayment.getAmount());
        existing.setPaymentMethod(updatedPayment.getPaymentMethod());
        existing.setStatus(updatedPayment.getStatus());

        return paymentRepository.save(existing);
    }

    public void deletePayment(Long paymentId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Payment payment = paymentRepository.findByIdAndUser(paymentId, user)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        paymentRepository.delete(payment);
    }

    public List<Payment> getPendingPayments(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return paymentRepository.findByUserAndStatus(user, Payment.PaymentStatus.PENDING);
    }
    public List<Payment> getPaymentsByUserId(Integer userId) {
        // Exemple simple
        return paymentRepository.findByUserId(userId);
    }

}