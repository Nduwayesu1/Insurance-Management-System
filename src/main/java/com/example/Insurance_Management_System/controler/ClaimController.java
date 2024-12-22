package com.example.Insurance_Management_System.controler;

import com.example.Insurance_Management_System.model.Claim;
import com.example.Insurance_Management_System.model.Enum.ClaimStatus;
import com.example.Insurance_Management_System.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/register")
    public String showCreateClaimForm(Model model) {
        model.addAttribute("claim", new Claim()); // Provide an empty Claim object for the form
        return "createClaim"; // Render the create_claim.html template
    }

    @PostMapping("/register")
    public String createClaim(@RequestParam("userId") Long userId,
                              @RequestParam("policyId") Long policyId,
                              @ModelAttribute("claim") Claim claim,
                              Model model) {
        try {
            // Save the claim with the associated userId and policyId
            Claim createdClaim = claimService.createClaim(userId, policyId, claim);
            model.addAttribute("message", "Claim created successfully!"); // Add success message
        } catch (IllegalArgumentException ex) {
            // Handle errors, such as invalid userId or policyId
            model.addAttribute("errorMessage", ex.getMessage()); // Add error message
            return "createClaim"; // Return to the form with the error message
        }
        model.addAttribute("claim", new Claim()); // Reset the form with an empty claim
        return "createClaim"; // Render the same form with success feedback
    }

    // Listing  all claims
    @GetMapping("/Dashboard")
    public String showDashboard(Model model) {
        List<Claim> claims = claimService.getAllClaims(); // Fetch all claims
        model.addAttribute("claims", claims); // Add claims to the model to pass to the view
        return "claimDashboard"; // Thymeleaf template for dashboard
    }


    @GetMapping("/updateStatus/{claimId}")
    public String updateClaimStatus(@PathVariable Long claimId,
                                    @RequestParam(name = "status") ClaimStatus status,
                                    Model model) {
        try {
            // Call the service to update the claim status
            claimService.updateClaimStatus(claimId, status);
            List<Claim> claims = claimService.getAllClaims(); // Fetch all claims
            model.addAttribute("claims", claims); // Add claims to the model to pass to the view
            // Add a success message and refresh the claims list
            model.addAttribute("message", "Claim status updated to " + status);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating claim status");
        }

        // Return the claims dashboard page
        return "claimDashboard"; // Ensure this is the correct view name
    }


}
