package com.example.naturelink.service;

import com.example.naturelink.dto.AuthResponse;
import com.example.naturelink.dto.SignInRequest;
import com.example.naturelink.dto.SignUpRequest;
import com.example.naturelink.entity.Role;
import com.example.naturelink.entity.User;
import com.example.naturelink.repository.UserRepository;
import com.example.naturelink.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
