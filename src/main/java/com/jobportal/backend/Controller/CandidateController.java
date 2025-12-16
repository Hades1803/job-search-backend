package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.CandidateProfileRequest;
import com.jobportal.backend.Entity.Candidate;
import com.jobportal.backend.Service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/me")
    public Candidate getMyProfile() {
        return candidateService.getMyProfile();
    }

    @PutMapping("/me")
    public Candidate updateMyProfile(@RequestBody CandidateProfileRequest request) {
        return candidateService.updateMyProfile(request);
    }
}
