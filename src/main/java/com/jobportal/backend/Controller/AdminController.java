package com.jobportal.backend.Controller;

import com.jobportal.backend.Entity.Candidate;
import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Service.CandidateService;
import com.jobportal.backend.Service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CandidateService candidateService;
    private final EmployerService employerService;

    @GetMapping("/candidates")
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidatesForAdmin();
    }

    @GetMapping("/employers")
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployersForAdmin();
    }
}

