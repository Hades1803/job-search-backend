package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.EmployerProfileRequest;
import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployerController {

    private final EmployerService employerService;

    @GetMapping("/me")
    public ResponseEntity<Employer> getMyEmployer() {
        return ResponseEntity.ok(employerService.getMyEmployer());
    }

    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employer> updateEmployer(
            @ModelAttribute EmployerProfileRequest request
    ) throws IOException {

        return ResponseEntity.ok(employerService.updateEmployer(request));
    }

    @GetMapping("/admin/employers")
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployersForAdmin();
    }
}
