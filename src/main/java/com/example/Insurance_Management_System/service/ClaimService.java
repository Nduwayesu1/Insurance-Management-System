package com.example.Insurance_Management_System.service;

import com.example.Insurance_Management_System.model.Claim;
import com.example.Insurance_Management_System.model.Enum.ClaimStatus;

import java.util.List;

public interface ClaimService {
    Claim createClaim(Long userId, Long policyId, Claim claim);
    public List<Claim> getAllClaims();
    public void updateClaimStatus(Long claimId, ClaimStatus status);
    Claim findClaimById(Long claimId);
}

