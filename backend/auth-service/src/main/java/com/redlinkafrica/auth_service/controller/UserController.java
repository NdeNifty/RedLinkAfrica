package com.redlinkafrica.auth_service.controller;

import com.redlinkafrica.auth_service.model.User;
import com.redlinkafrica.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // POST /api/auth/register - Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("username already exists");
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // POST /api/auth/login - Authenticate user (placeholder for token)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent() && user.get().getPasswordHash().equals(loginRequest.getPasswordHash())) {
            // In a real app, generate and return a JWT token here
            return ResponseEntity.ok("Auth successful - Token placeholder");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // GET /api/auth/users/{id} - Get user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/auth/users/{id} - Update user
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setPasswordHash(userDetails.getPasswordHash());
            updatedUser.setRole(userDetails.getRole());
            updatedUser.setDonorId(userDetails.getDonorId());
            updatedUser.setTwoFactorEnabled(userDetails.isTwoFactorEnabled());
            return ResponseEntity.ok(userRepository.save(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/auth/users/donor/{donorId} - Get user by donor ID
    @GetMapping("/users/donor/{donorId}")
    public ResponseEntity<User> getUserByDonorId(@PathVariable String donorId) {
        Optional<User> user = userRepository.findByDonorId(donorId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}