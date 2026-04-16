package com.qm.auth.service;
import com.qm.auth.dto.AuthResponse;
import com.qm.auth.dto.GoogleUserInfo;
import com.qm.auth.dto.LoginRequest;
import com.qm.auth.dto.SignupRequest;
import com.qm.auth.model.User;
import com.qm.auth.repository.UserRepository;
import com.qm.auth.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service  // Mark as Spring service component
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    
    public AuthService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder, 
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

  
 
    public AuthResponse login(LoginRequest request) {
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

         
        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

         
        String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getId()));
 
        return new AuthResponse(token, user.getUsername(), String.valueOf(user.getId()));
    }

   
 
    public AuthResponse signup(SignupRequest request) {
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
 
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
 
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        user.setProvider("local");  // Mark as local account

         
        User savedUser = userRepository.save(user);

        
        String token = jwtUtil.generateToken(savedUser.getUsername(), String.valueOf(savedUser.getId()));

        return new AuthResponse(token, savedUser.getUsername(), String.valueOf(savedUser.getId()));
    }

     
    public AuthResponse processGoogleUser(GoogleUserInfo googleUser) {
        
        User user = userRepository.findByProviderAndProviderId("google", googleUser.getId())
                .orElseGet(() -> {
                    
                    User newUser = new User();
                    newUser.setUsername(googleUser.getEmail());
                    newUser.setEmail(googleUser.getEmail());
                    newUser.setProvider("google");
                    newUser.setProviderId(googleUser.getId());
                    
                    newUser.setPassword(passwordEncoder.encode("GOOGLE_OAUTH_USER"));
                    
                    return userRepository.save(newUser);
                });
 
        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        
        String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getId()));

        return new AuthResponse(token, user.getUsername(), String.valueOf(user.getId()));
    }

     

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
