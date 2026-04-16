package com.qm.auth.service;

import com.qm.auth.model.User;
import com.qm.auth.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * UserDetailsService Implementation
 * 
 * Spring Security uses this interface to load user data during authentication.
 * Called by JwtFilter when validating tokens.
 */
@Service  // Spring auto-detects and registers
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user by username
     * Called by JwtFilter after validating JWT token
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        /**
         * Convert our User entity to Spring Security UserDetails
         * 
         * Spring Security's UserDetails interface:
         * - getUsername() - returns username
         * - getPassword() - returns hashed password
         * - getAuthorities() - returns roles/permissions
         */
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    /**
     * Load user by ID
     * Useful for other services that receive userId
     */
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
