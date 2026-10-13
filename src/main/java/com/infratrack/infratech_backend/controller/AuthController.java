
package com.infratrack.infratech_backend.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infratrack.infratech_backend.JwtService;
import com.infratrack.infratech_backend.dto.LoginRequest;
import com.infratrack.infratech_backend.dto.LoginResponse;
import com.infratrack.infratech_backend.dto.RegisterRequest;
import com.infratrack.infratech_backend.dto.RegisterResponse;
import com.infratrack.infratech_backend.model.UserAccount;
import com.infratrack.infratech_backend.repository.UserAccountRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccountRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();
    private final JwtService jwtService;

    public AuthController(
            UserAccountRepository userRepository,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            "An account with this email already exists."));
        }

        UserAccount user = new UserAccount();
        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword()));

        user.setRole("USER");
        user.setCreatedAt(Instant.now());

        UserAccount saved = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new RegisterResponse(
                        saved.getId(),
                        saved.getFullName(),
                        saved.getEmail(),
                        saved.getRole(),
                        saved.getCreatedAt()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        UserAccount user = userRepository.findByEmailIgnoreCase(email)
                .orElse(null);

        // Diagnostic logs: never print passwords or password hashes.
        System.out.println("[LOGIN DEBUG] User found: " + (user != null));

        if (user == null) {
            System.out.println(
                    "[LOGIN DEBUG] Email lookup did not find a user.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message", "Invalid email or password"));
        }

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash());

        System.out.println(
                "[LOGIN DEBUG] Password matches: " + passwordMatches);

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message", "Invalid email or password"));
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole());

        Map<String, Object> userData = Map.of(
                "id", user.getId(),
                "name", user.getFullName(),
                "email", user.getEmail(),
                "phone", "",
                "role", user.getRole(),
                "active", true);

        return ResponseEntity.ok(
                new LoginResponse(token, userData));
    }
}
