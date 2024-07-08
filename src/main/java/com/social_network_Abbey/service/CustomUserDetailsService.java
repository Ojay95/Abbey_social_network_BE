package com.social_network_Abbey.service;

import com.social_network_Abbey.entity.ApplicationUser;
import com.social_network_Abbey.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(applicationUser.getUsername())
                .password(applicationUser.getPassword())
                .authorities(new ArrayList<>()) // Assuming no specific authorities for now
                .build();
        }

        public UserDetails loadUserById(Long id) {
            ApplicationUser applicationUser = applicationUserRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

            return org.springframework.security.core.userdetails.User.builder()
                    .username(applicationUser.getUsername())
                    .password(applicationUser.getPassword())
                    .authorities(new ArrayList<>()) // Assuming no specific authorities for now
                    .build();
        }
}
