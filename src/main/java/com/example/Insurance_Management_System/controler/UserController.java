package com.example.Insurance_Management_System.controler;
import com.example.Insurance_Management_System.Repository.UserRepository;
import com.example.Insurance_Management_System.model.Payment;
import com.example.Insurance_Management_System.model.User;
import com.example.Insurance_Management_System.service.PaymentService;
import com.example.Insurance_Management_System.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private  final  PaymentService paymentService;
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;  // Assumed that UserRepository is defined for accessing the database
    @Autowired
    public UserController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Provide an empty user object for the form
        return "register"; // Renders the register.html template
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            // Try to register the user
            userService.registerUser(user);
            model.addAttribute("message", "User registered successfully!"); // Add success message
        } catch (IllegalArgumentException ex) {
            // If there's an error (like duplicate contact), add it to the model
            model.addAttribute("errorMessage", ex.getMessage());
            return "register"; // Return to the form with the error message
        }
        model.addAttribute("user", new User()); // Reset the form with an empty user
        return "register"; // Renders the same form with success feedback
    }
   // Listing users Method
    @GetMapping("/usersList")
    public String getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction, // default ascending
            Model model,
            HttpSession session) {

        String loggedInUsername = (String) session.getAttribute("loggedInUsername");

        Page<User> userPage = userService.getPaginatedUsers(page, size, sortBy, direction);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("message", "Welcome, " + (loggedInUsername != null ? loggedInUsername : "Guest") + "!");

        return "userDashboard";
    }

    // Endpoint for deleting a user
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        try {
//            userService.deleteById(id);
//            redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Error deleting user.");
//        }
//        return "redirect:/users/usersList";
//    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        // Retrieve the userId from the session (the logged-in user)
        Long loggedInUserId = (Long) session.getAttribute("userId");

        // Check if the logged-in user is the same as the user attempting to be deleted
        if (loggedInUserId != null && loggedInUserId.equals(id)) {
            try {
                // Proceed with the deletion of the user account
                userService.deleteById(id);
                redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
                return "redirect:/logout"; // Redirect to logout or home page after successful deletion
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error deleting user.");
            }
        } else {
            // If the logged-in user is trying to delete someone else's account, deny the action
            redirectAttributes.addFlashAttribute("error", "You are not authorized to delete this account.");
            return "403"; // Redirect to a 403 error page or a custom unauthorized page
        }

        return "redirect:/users/usersList"; // Return to users list or previous page
    }


    // Update user method
    @GetMapping("/edit/{userId}")
    public String showEditForm(@PathVariable Long userId, Model model) {
        try {
            User user = userService.findById(userId);
            model.addAttribute("user", user);
            return "edit-user"; // Name of the HTML file (edit-user.html)
        } catch (Exception e) {
            model.addAttribute("error", "User not found: " + e.getMessage());
            return "redirect:/users/usersList";
        }
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, Model model) {
        try {
            // Update the user using the service
            userService.updateUser(user);

            // Add a success message
            model.addAttribute("message", "User updated successfully!");
        } catch (Exception e) {
            // Handle errors
            model.addAttribute("error", "Failed to update user: " + e.getMessage());
        }

        // Redirect to the user list
        return "redirect:/users/usersList";
    }



}







