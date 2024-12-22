package com.example.Insurance_Management_System.Repository;

import com.example.Insurance_Management_System.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paymentRepository extends JpaRepository<Payment,Long> {
}
