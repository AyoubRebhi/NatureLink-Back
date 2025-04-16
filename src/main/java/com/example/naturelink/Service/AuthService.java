package com.example.naturelink.Service;

import com.example.naturelink.dto.AuthResponse;
import com.example.naturelink.dto.SignInRequest;
import com.example.naturelink.dto.SignUpRequest;
import com.example.naturelink.Entity.Role;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Exceptions.DuplicateEntityException;
import com.example.naturelink.Repository.UserRepository;
import com.example.naturelink.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponse signUp(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateEntityException("Username already exists");
        }

        // Check for existing email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEntityException("Email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return new AuthResponse(jwtUtils.generateToken(user));  // No need for casting to UserDetails, as User implements UserDetails
    }

    public AuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return new AuthResponse(jwtUtils.generateToken(user));  // No need for casting to UserDetails, as User implements UserDetails
    }

}
