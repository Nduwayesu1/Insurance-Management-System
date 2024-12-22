package com.example.Insurance_Management_System.service;
import com.example.Insurance_Management_System.Repository.UserRepository;
import com.example.Insurance_Management_System.Repository.paymentRepository;
import com.example.Insurance_Management_System.Repository.policyRepository;
import com.example.Insurance_Management_System.model.Payment;
import com.example.Insurance_Management_System.model.Policy;
import com.example.Insurance_Management_System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final paymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final policyRepository policyRepository;

    @Autowired
    public PaymentServiceImpl(paymentRepository paymentRepository, UserRepository userRepository, policyRepository policyRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public Payment makePayment(Long userId, Long policyId, Payment payment) {
        // Find User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // Find Policy
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid policy ID"));

        // Set relationships
        payment.setUser(user);
        payment.setPolicy(policy);

        // Save payment
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}