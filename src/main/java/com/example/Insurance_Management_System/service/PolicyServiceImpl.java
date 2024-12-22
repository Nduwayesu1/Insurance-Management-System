package com.example.Insurance_Management_System.service;
import com.example.Insurance_Management_System.Repository.UserRepository;
import com.example.Insurance_Management_System.Repository.policyRepository;
import com.example.Insurance_Management_System.model.Policy;
import com.example.Insurance_Management_System.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final policyRepository policyRepository;
    private final UserRepository userRepository;

    @Autowired
    public PolicyServiceImpl(policyRepository policyRepository, UserRepository userRepository) {
        this.policyRepository = policyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Policy savePolicy(Long userId, Policy policy) {
        // Fetch the user from the User table
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " does not exist."));

        // Associate the fetched User with the Policy
        policy.setUser(user);

        // Save the Policy
        return policyRepository.save(policy);
    }
}