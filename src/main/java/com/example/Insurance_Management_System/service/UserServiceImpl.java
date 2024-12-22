package com.example.Insurance_Management_System.service;

import com.example.Insurance_Management_System.Repository.UserRepository;
import com.example.Insurance_Management_System.Repository.paymentRepository;
import com.example.Insurance_Management_System.model.Payment;
import com.example.Insurance_Management_System.model.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final paymentRepository  paymentRepository;
    @Autowired
    private EmailService emailService;  // Inject the email service

    @Autowired
    public UserServiceImpl(UserRepository userRepository, com.example.Insurance_Management_System.Repository.paymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public User registerUser(User user) {
        // Check if the email already exists in the database
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists!");
        }

        // Check if the contact already exists in the database
        if (userRepository.findByContact(user.getContact()) != null) {
            throw new IllegalArgumentException("Contact already exists!");
        }

        // Validate the contact number format
        if (!isValidContactNumber(user.getContact())) {
            throw new IllegalArgumentException("Invalid contact number! It should start with 078, 079, 072, or 073 and be 10 digits long.");
        }

        // Send a welcome email before saving the user
        sendWelcomeEmail(user);

        // Set the password directly without encoding
        user.setPassword(user.getPassword());

        // Save the user to the repository
        return userRepository.save(user);
    }


    private boolean isValidContactNumber(String contact) {
        // Validates if the contact number follows the required pattern
        return contact.matches("^(078|079|072|073)\\d{7}$");
    }

    @Override
    public User login(String email, String password, String role) {
        User existingUser = findByEmail(email);

        if (existingUser != null &&
                password.equals(existingUser.getPassword()) && // Direct comparison without encoding
                existingUser.getRole().equals(role)) {
            return existingUser; // Return the authenticated user
        }

        return null; // Return null if no user found or credentials don't match
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> gettAll() {
        return userRepository.findAll();
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) throws Exception {

        // Fetch the existing user from the database
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        // Update the fields
        existingUser.setName(user.getName());
        existingUser.setAddress(user.getAddress());
        existingUser.setContact(user.getContact());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        // Save the updated user
        return  userRepository.save(existingUser);
    }

    public Page<User> getPaginatedUsers(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }


    private void sendWelcomeEmail(User user) {
        try {
            String subject = "Welcome to Our Service!";
            String text = "Dear " + user.getName() + ",\n\n" +
                    "Thank you for registering. Your account will be created shortly.\n\n" +
                    "Best regards,\n" +
                    "Technical Team";

            // Send the email
            emailService.sendEmail(user.getEmail(), subject, text);
        } catch (MessagingException e) {
            // Handle any error that occurs while sending the email
            System.out.println("Error sending welcome email: " + e.getMessage());
        }
    }

}
