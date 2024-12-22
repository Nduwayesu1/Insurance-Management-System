package com.example.Insurance_Management_System.Repository;

import com.example.Insurance_Management_System.model.Policy;
import com.example.Insurance_Management_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface policyRepository extends JpaRepository<Policy,Long> {
}
