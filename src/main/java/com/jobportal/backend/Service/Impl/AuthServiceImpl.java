package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.AuthRequest;
import com.jobportal.backend.Dto.AuthResponse;
import com.jobportal.backend.Dto.RegisterRequest;
import com.jobportal.backend.Entity.*;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.CandidateRepo;
import com.jobportal.backend.Repository.EmployerRepo;
import com.jobportal.backend.Repository.RoleRepo;
import com.jobportal.backend.Security.JwtUtil;
import com.jobportal.backend.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepo accountRepo;
    private final RoleRepo roleRepo;
    private final CandidateRepo candidateRepo;
    private final EmployerRepo employerRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public String register(RegisterRequest request) {   
        // Check if email already exists
        if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        Role role = roleRepo.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Create account
        Account acc = new Account();
        acc.setEmail(request.getEmail());
        acc.setPassword(passwordEncoder.encode(request.getPassword()));
        acc.setRole(role);
        acc.setStatus(true);
        acc.setCreatedAt(LocalDateTime.now());

        Account savedAccount = accountRepo.save(acc);

        // Create profile based on role type
        if (role.getName() == RoleType.CANDIDATE) {
            createCandidateProfile(savedAccount);
        } else if (role.getName() == RoleType.EMPLOYER) {
            createEmployerProfile(savedAccount);
        }
        return "Registration successful!";
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Account acc = accountRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!acc.getStatus()) {
            throw new IllegalStateException("Account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), acc.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String accessToken = jwtUtil.generateToken(acc.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .email(acc.getEmail())
                .role(acc.getRole().getName().name())
                .expiresIn(jwtUtil.getExpiration())
                .message("Login successful")
                .build();
    }


    private void createCandidateProfile(Account account) {
        Candidate candidate = new Candidate();
        candidate.setAccount(account);

        candidateRepo.save(candidate);
    }

    private void createEmployerProfile(Account account) {
        Employer employer = new Employer();
        employer.setAccount(account);
        employerRepo.save(employer);
    }

}