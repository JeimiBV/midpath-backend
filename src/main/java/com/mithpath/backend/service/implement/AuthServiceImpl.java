package com.mithpath.backend.service.implement;

import com.mithpath.backend.dto.AuthDto;
import com.mithpath.backend.exception.DuplicateException;
import com.mithpath.backend.exception.MessageUtil;
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

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User register(AuthDto dto) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            String message = MessageUtil.getProperty("exception.authentication.duplicateUsername.message", dto.getUsername());
            throw new DuplicateException(message);
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            String message = MessageUtil.getProperty("exception.authentication.duplicateEmail.message", dto.getEmail());
            throw new DuplicateException(message);
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public Map<String, String> login(AuthDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid username or password");

        String token = jwtUtil.generateToken(user.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
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
