package com.example.Insurance_Management_System.Repository;

import jdk.jfr.Registered;
import com.example.Insurance_Management_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Registered
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    User findByContact(String contact);

}
