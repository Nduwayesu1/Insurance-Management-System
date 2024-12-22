package com.example.Insurance_Management_System.model;

import jakarta.persistence.*;
import com.example.Insurance_Management_System.model.Enum.ClaimStatus;



@Entity
@Table(name = "Claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long claimId;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status; // Enum: Pending, Approved, Rejected

    private Double claimAmount;
    private String remarks;
    private String claimDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public Claim() {

    }

    public Claim(Long claimId) {
        this.claimId = claimId;
    }

    public Claim(ClaimStatus status, Double claimAmount, String remarks, String claimDate, User user, Long claimId, Policy policy) {
        this.status = status;
        this.claimAmount = claimAmount;
        this.remarks = remarks;
        this.claimDate = claimDate;
        this.user = user;
        this.claimId = claimId;
        this.policy = policy;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
