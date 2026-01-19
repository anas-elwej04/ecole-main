package com.school.gestionscolarite.controller;

import com.school.gestionscolarite.dto.AuthResponse;
import com.school.gestionscolarite.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    // Login endpoint for frontend compatibility
    @PostMapping("/api/login")
    public ResponseEntity<?> loginDirect(@RequestBody LoginRequest loginRequest) {
        return performLogin(loginRequest);
    }

    // Original auth login endpoint
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return performLogin(loginRequest);
    }

    private ResponseEntity<?> performLogin(LoginRequest loginRequest) {
        System.out.println("Login request received for: " + loginRequest.getEmail());

        if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Error: 'email' is missing or empty.");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Error: 'password' is missing or empty.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(item -> item.getAuthority())
                .orElse("UNKNOWN");

        return ResponseEntity.ok(new AuthResponse("Login successful", authentication.getName(), role));
    }
}
