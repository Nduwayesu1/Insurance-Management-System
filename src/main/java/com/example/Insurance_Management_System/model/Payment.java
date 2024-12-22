package com.example.Insurance_Management_System.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String paymentDate;
    private Double amountPaid;
    private String dueDate;
    private String receiptNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    // Getters and Setters


    public Payment() {
    }

    public Payment(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Payment(Long paymentId, String paymentDate, Double amountPaid, String dueDate, String receiptNumber, User user, Policy policy) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.dueDate = dueDate;
        this.receiptNumber = receiptNumber;
        this.user = user;
        this.policy = policy;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
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
