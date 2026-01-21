package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Dto.JobApplicationResponse;
import com.jobportal.backend.Dto.MyApplicationResponse;
import com.jobportal.backend.Dto.ResumeResponse;
import com.jobportal.backend.Entity.Application;
import com.jobportal.backend.Entity.ApplicationStatus;
import com.jobportal.backend.Service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/employer/job/{jobId}")
    public List<JobApplicationResponse> getApplicationsByJob(
            @PathVariable Integer jobId
    ) {
        return applicationService.getApplicationsByJob(jobId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Integer id,
            @RequestParam ApplicationStatus status
    ) {
        applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.ok("Cập nhật trạng thái ứng tuyển thành công");
    }


    @GetMapping("/{applicationId}/resume")
    public ResponseEntity<ResumeResponse> getResume(@PathVariable Integer applicationId) {
        ResumeResponse resumeContentOrLink = applicationService.getResumeByApplicationId(applicationId);
        return ResponseEntity.ok(resumeContentOrLink);
    }


}
