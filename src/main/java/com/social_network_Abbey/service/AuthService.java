package com.social_network_Abbey.service;

import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.repository.ApplicationUserRepository;
import com.social_network_Abbey.request.AuthRequest;
import com.social_network_Abbey.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void ApplicationUser(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder,
                                AuthenticationManager authenticationManager, JwtService jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtUtil;
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtService.generateToken((UserDetails) userDetails);

        return new AuthResponse();
    }

    public ApplicationUser registerUser(ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return applicationUserRepository.save(user);
    }
}