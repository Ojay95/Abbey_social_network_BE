package com.social_network_Abbey.controller;

import com.social_network_Abbey.dto.UserDTO;
import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class ApplicationUserController {

    @Autowired
    private ApplicationUserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllApplicationUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApplicationUser> getUserById(@PathVariable Long userId) {
        ApplicationUser user = (ApplicationUser) userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Optional<ApplicationUser>> getUserByUsername(@PathVariable String username) {
        Optional<Optional<ApplicationUser>> user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<ApplicationUser> searchUsersByUsername(@RequestParam String username) {
        return userService.searchUsersByUsername(username);
    }

    @PostMapping
    public ResponseEntity<ApplicationUser> createUser(@RequestBody ApplicationUser newUser) {
        ApplicationUser createdUser = (ApplicationUser) userService.createUser((ApplicationUser) newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApplicationUser> updateUser(@PathVariable Long userId, @RequestBody ApplicationUser updatedUser) {
        ApplicationUser user = userService.updateUser(userId, (ApplicationUser) updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
