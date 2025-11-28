package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.AuthRequest;
import com.jobportal.backend.Dto.RegisterRequest;
import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Role;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.RoleRepo;
import com.jobportal.backend.Security.JwtUtil;
import com.jobportal.backend.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepo accountRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Changed from JwtService

    @Override
    public String register(RegisterRequest request) {
        // Check if email already exists
        if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        Role role = roleRepo.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        Account acc = new Account();
        acc.setEmail(request.getEmail());
        acc.setPassword(passwordEncoder.encode(request.getPassword()));
        acc.setRole(role);
        acc.setStatus(true);
        acc.setCreatedAt(LocalDateTime.now());
        accountRepo.save(acc);

        return "Registration successful!";
    }

    @Override
    public String login(AuthRequest request) {
        Account acc = accountRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!acc.getStatus()) {
            throw new IllegalStateException("Account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), acc.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtUtil.generateToken(acc.getEmail());
    }
}