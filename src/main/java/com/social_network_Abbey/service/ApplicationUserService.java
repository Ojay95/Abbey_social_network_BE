package com.social_network_Abbey.service;

import com.social_network_Abbey.dto.UserDTO;
import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.repository.ApplicationUserRepository;
import com.social_network_Abbey.repository.FollowRepository;
import com.social_network_Abbey.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService {

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    public void UserService(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public List<UserDTO> getAllApplicationUsers() {
        List<ApplicationUser> users = userRepository.findAll();

        List<UserDTO> allUsers = new ArrayList<>();

        for (ApplicationUser user : users) {
            UserDTO dtoUser = new UserDTO();
            dtoUser.setId(user.getId());
            dtoUser.setUsername(user.getUsername());
            dtoUser.setEmail(user.getEmail());
            dtoUser.setFirstName(user.getFirstName());
            dtoUser.setLastName(user.getLastName());
            allUsers.add(dtoUser);
        }

        return allUsers;
    }

    public ApplicationUser getUserById(Long userId) {
        Optional<ApplicationUser> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public Optional<Optional<ApplicationUser>> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public List<ApplicationUser> searchUsersByUsername(String username) {
        return userRepository.findAllByFirstName(username);
    }

    // Create a new user
    public ApplicationUser createUser(ApplicationUser newUser) {
        return userRepository.save(newUser);
    }

    // Update an existing user
    public ApplicationUser updateUser(Long userId, ApplicationUser updatedUser) {
        Optional<ApplicationUser> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            ApplicationUser existingUser = existingUserOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean isFollowing(Long userId, Long followingId) {
        return followRepository.findByFollower_IdAndFollowing_Id(userId, followingId).isPresent();
    }

    public void saveUser(ApplicationUser user) {
        userRepository.save(user);
    }



    public String authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                        authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails);
    }
}
