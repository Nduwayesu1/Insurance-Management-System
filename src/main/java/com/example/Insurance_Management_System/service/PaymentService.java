package com.example.Insurance_Management_System.service;
import com.example.Insurance_Management_System.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment makePayment(Long userId, Long policyId, Payment payment);

    List<Payment> getAllPayments() ;
}