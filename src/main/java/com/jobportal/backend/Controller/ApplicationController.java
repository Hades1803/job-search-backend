package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Dto.MyApplicationResponse;
import com.jobportal.backend.Entity.Application;
import com.jobportal.backend.Service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApplicationController {

    private final ApplicationService applicationService;


    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Application applyJob(@ModelAttribute ApplyJobRequest request) {
        return applicationService.applyJob(request);
    }

    @GetMapping("/my")
    public List<MyApplicationResponse> getMyAppliedJobs() {
        return applicationService.getMyApplications();
    }

}
