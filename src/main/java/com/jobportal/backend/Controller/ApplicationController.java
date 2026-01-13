package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Entity.Application;
import com.jobportal.backend.Service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply")
    public Application applyJob(@RequestBody ApplyJobRequest request) {
        return applicationService.applyJob(request);
    }
}
