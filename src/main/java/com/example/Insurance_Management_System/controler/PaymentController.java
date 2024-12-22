package com.example.Insurance_Management_System.controler;

import com.example.Insurance_Management_System.model.Payment;
import com.example.Insurance_Management_System.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Display Payment Form
    @GetMapping("/make")
    public String showMakePaymentForm(Model model) {
        model.addAttribute("payment", new Payment()); // Provide an empty Payment object for the form
        return "makePayment"; // Render the make_payment.html template
    }

    // Handle Payment Submission
    @PostMapping("/make")
    public String makePayment(@RequestParam("userId") Long userId,
                              @RequestParam("policyId") Long policyId,
                              @ModelAttribute("payment") Payment payment,
                              Model model) {
        try {
            // Process the payment with the associated userId and policyId
            Payment savedPayment = paymentService.makePayment(userId, policyId, payment);
            model.addAttribute("message", "Payment successfully completed!"); // Add success message
        } catch (IllegalArgumentException ex) {
            // Handle errors, such as invalid userId or policyId
            model.addAttribute("errorMessage", ex.getMessage()); // Add error message
            return "makePayment"; // Return to the form with the error message
        }
        model.addAttribute("payment", new Payment()); // Reset the form with an empty Payment object
        return "makePayment"; // Render the same form with success feedback
    }

    @GetMapping("/payments")
    public String getAllPayments(Model model) {

        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "adminPaymentsDashboard";
    }
}
