package com.mithpath.backend.service.implement;

import com.mithpath.backend.dto.AuthDto;
import com.mithpath.backend.model.User;
import com.mithpath.backend.repository.UserRepository;
import com.mithpath.backend.security.JwtUtil;
import com.mithpath.backend.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User register(AuthDto dto) {
        if(userRepository.existsByUsername(dto.getUsername()))
            throw new IllegalArgumentException("Username already exists");
        if(userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(AuthDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid username or password");

        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public User getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
