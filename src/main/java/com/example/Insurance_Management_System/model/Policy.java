package com.example.Insurance_Management_System.model;

import jakarta.persistence.*;
import com.example.Insurance_Management_System.model.Enum.PolicyType;

import java.util.List;

// Define the Policy entity
@Entity
@Table(name = "Policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @Enumerated(EnumType.STRING)
    private PolicyType policyType; // Enum: Health, Life, Vehicle, etc.

    private String policyName;
    private String policyDetails;
    private Double premiumAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Claim> claims;

    @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    // Getters and Setters

    public Policy() {
    }

    public Policy(Long policyId) {
        this.policyId = policyId;
    }

    public Policy(Long policyId, PolicyType policyType, String policyName, String policyDetails, User user, Double premiumAmount, List<Claim> claims, List<Payment> payments) {
        this.policyId = policyId;
        this.policyType = policyType;
        this.policyName = policyName;
        this.policyDetails = policyDetails;
        this.user = user;
        this.premiumAmount = premiumAmount;
        this.claims = claims;
        this.payments = payments;
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyDetails() {
        return policyDetails;
    }

    public void setPolicyDetails(String policyDetails) {
        this.policyDetails = policyDetails;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
