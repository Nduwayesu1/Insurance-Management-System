package com.example.Insurance_Management_System.controler;
import com.example.Insurance_Management_System.model.Claim;
import com.example.Insurance_Management_System.model.Enum.ClaimStatus;
import com.example.Insurance_Management_System.model.User;
import com.example.Insurance_Management_System.service.ClaimService;
import com.example.Insurance_Management_System.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private ClaimService claimService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());  // Add an empty User object to the model
        return "login"; // Return the login page
    }
    //login
    @PostMapping("/post-login")
    public String postLogin(@ModelAttribute("user") User user, HttpSession session) {
        // Authenticate the user based on the provided email, password, and role
        User authenticatedUser = userService.login(user.getEmail(), user.getPassword(), user.getRole());

        if (authenticatedUser != null) {
            // Store the user's role, username, and userId in the session after successful login
            session.setAttribute("role", authenticatedUser.getRole());
            session.setAttribute("loggedInUsername", authenticatedUser.getName());
            session.setAttribute("userId", authenticatedUser.getUserId());

            // Redirect based on the user's role
            if ("ADMIN".equals(authenticatedUser.getRole())) {
                return "redirect:/payments/payments"; // Redirect to admin dashboard
            } else if ("CUSTOMER".equals(authenticatedUser.getRole())) {
                return "redirect:/users/usersList"; // Redirect to customer dashboard
            } else {
                // Redirect to 403 page for other roles
                return "redirect:/403";
            }
        } else {
            // If authentication failed, redirect back to login with an error message
            return "redirect:/login?error=true";
        }
    }
    // logout
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Invalidate the current session to log the user out
        request.getSession().invalidate();
        return "redirect:/login";  // Redirect to the login page
    }

}
