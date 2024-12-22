package com.example.Insurance_Management_System.service;

import com.example.Insurance_Management_System.Repository.UserRepository;
import com.example.Insurance_Management_System.Repository.claimRepository;
import com.example.Insurance_Management_System.Repository.policyRepository;
import com.example.Insurance_Management_System.model.Claim;
import com.example.Insurance_Management_System.model.Enum.ClaimStatus;
import com.example.Insurance_Management_System.model.Policy;
import com.example.Insurance_Management_System.model.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private claimRepository claimRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private policyRepository policyRepository;

    @Override
    public Claim createClaim(Long userId, Long policyId, Claim claim) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        claim.setUser(user);
        claim.setPolicy(policy);
        claim.setStatus(ClaimStatus.PENDING); // Default status

        return claimRepository.save(claim);
    }
    @Autowired
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    public void updateClaimStatus(Long claimId, ClaimStatus status) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        claim.setStatus(ClaimStatus.valueOf(String.valueOf(status))); // Set the new status (ACCEPTED or REJECTED)
        claimRepository.save(claim); // Save the updated claim
    }



    @Override
    public Claim findClaimById(Long claimId) {
        return claimRepository.findById(claimId).orElse(null);
    }


    }

