package com.jobportal.backend.Controller;

import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
@CrossOrigin
public class EmployerController {

    private final EmployerService employerService;
    private final AccountRepo accountRepo;
    @PostMapping

    public Employer createEmployer(
            @RequestBody Employer employer,
            Authentication authentication) {

        String email = authentication.getName();
        Account account = accountRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return employerService.createEmployer(employer, account);
    }

    @PutMapping
    public Employer updateEmployer(
            @RequestBody Employer employer,
            Authentication authentication) {

        // Lấy email từ Authentication
        String email = authentication.getName();

        // Lấy Account managed từ DB
        Account managedAccount = accountRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Truyền managedAccount vào service
        return employerService.updateEmployer(employer, managedAccount);
    }


    @GetMapping("/me")

    public Employer getMyEmployer(Authentication authentication) {

        Account account = (Account) authentication.getPrincipal();
        return employerService.getMyEmployer(account);
    }
}
