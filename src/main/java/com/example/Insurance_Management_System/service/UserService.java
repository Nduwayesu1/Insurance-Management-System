package com.example.Insurance_Management_System.service;

import com.example.Insurance_Management_System.model.Payment;
import com.example.Insurance_Management_System.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User login(String username,String pasword, String role); // To login the user

    User findByEmail(String email);
    public List <User> gettAll();
    User findById(Long id);
    void deleteById(Long id);
    User updateUser(User user) throws Exception;

    Page<User> getPaginatedUsers(int page, int size,String sortBy, String direction);
}
