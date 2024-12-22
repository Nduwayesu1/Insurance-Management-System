package com.example.Insurance_Management_System.controler;
import com.example.Insurance_Management_System.model.Policy;
import com.example.Insurance_Management_System.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/register")
    public String showCreatePolicyForm(Model model) {
        model.addAttribute("policy", new Policy()); // Provide an empty Policy object for the form
        return "registerPolicy"; // Renders the create_policy.html template
    }

    @PostMapping("/register")
    public String createPolicy(@RequestParam("userId") Long userId, @ModelAttribute("policy") Policy policy, Model model) {
        try {
            // Save the policy with the associated userId
            policyService.savePolicy(userId, policy);
            model.addAttribute("message", "Policy created successfully!"); // Add success message
        } catch (IllegalArgumentException ex) {
            // Handle errors, such as invalid userId
            model.addAttribute("errorMessage", ex.getMessage()); // Add error message
            return "registerPolicy"; // Return to the form with the error message
        }
        model.addAttribute("policy", new Policy()); // Reset the form with an empty policy
        return "registerPolicy"; // Renders the same form with success feedback
    }
}

