package com.social_network_Abbey.controller;

import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.request.AuthRequest;
import com.social_network_Abbey.request.RegisterRequest;
import com.social_network_Abbey.response.AuthResponse;
import com.social_network_Abbey.service.ApplicationUserService;
import com.social_network_Abbey.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService userService;
    @Autowired
    private AuthService authService;

    @Autowired
    public AuthController(ApplicationUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws AuthenticationException {
        AuthResponse authResponse = authService.authenticateUser(authRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        ApplicationUser newUser = new ApplicationUser();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setFirstName(registerRequest.getFirstName());
        newUser.setLastName(registerRequest.getLastName());

        userService.createUser(newUser);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest authRequest) {
        String token = userService.authenticate(authRequest);
        return ResponseEntity.ok(token);
    }
}
