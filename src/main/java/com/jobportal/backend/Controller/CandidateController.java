package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.CandidateProfileRequest;
import com.jobportal.backend.Entity.Candidate;
import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/me")
    public ResponseEntity<Candidate> getMyProfile() {
        return ResponseEntity.ok(candidateService.getMyProfile());
    }


    @PutMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Candidate> updateMyProfile(
            @ModelAttribute CandidateProfileRequest request
    ) throws IOException {
        return ResponseEntity.ok(candidateService.updateMyProfile(request));
    }

    @GetMapping("/admin/candidates")
    public List<Candidate> getAllEmployers() {
        return candidateService.getAllCandidatesForAdmin();
    }
}
