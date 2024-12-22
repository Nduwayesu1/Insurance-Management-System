package com.example.Insurance_Management_System.service;
import com.example.Insurance_Management_System.model.Policy;

public interface PolicyService {
    Policy savePolicy(Long userId, Policy policy);
}