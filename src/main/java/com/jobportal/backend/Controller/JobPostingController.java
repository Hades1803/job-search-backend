package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.JobPostingRequest;
import com.jobportal.backend.Dto.JobPostingResponse;
import com.jobportal.backend.Entity.JobPosting;
import com.jobportal.backend.Service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/job-postings")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    // ========== PUBLIC ENDPOINTS ==========

    @GetMapping("/public")
    public ResponseEntity<Page<JobPostingResponse>> getAllActiveJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort sort = direction.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<JobPostingResponse> jobs = jobPostingService.getAllActiveJobPostings(pageable);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<JobPostingResponse> getJobById(@PathVariable Integer id) {
        JobPostingResponse job = jobPostingService.getJobPostingById(id);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/public/search")
    public ResponseEntity<Page<JobPostingResponse>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String majorId,
            @RequestParam(required = false) String jobTypeId,
            @RequestParam(required = false) String addressId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("postedDate").descending());

        Page<JobPostingResponse> jobs = jobPostingService.searchJobPostings(
                keyword, majorId, jobTypeId, addressId, pageable);

        return ResponseEntity.ok(jobs);
    }

    // ========== EMPLOYER ENDPOINTS ==========

    @PostMapping("/employer")
    public ResponseEntity<JobPosting> createJob(@RequestBody JobPostingRequest request) {
        JobPosting job = jobPostingService.createJobPosting(request);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/employer/{id}")
    public ResponseEntity<JobPosting> updateJob(
            @PathVariable Integer id,
            @RequestBody JobPostingRequest request) {
        JobPosting job = jobPostingService.updateJobPosting(id, request);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/employer/{id}")
    public ResponseEntity<Map<String, String>> deleteJob(@PathVariable Integer id) {
        jobPostingService.deleteJobPosting(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Xóa bài đăng tuyển dụng thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employer/my-jobs")
    public ResponseEntity<List<JobPosting>> getMyJobs() {
        List<JobPosting> jobs = jobPostingService.getMyJobPostings();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/employer/my-jobs/{id}")
    public ResponseEntity<JobPosting> getMyJobById(@PathVariable Integer id) {
        JobPosting job = jobPostingService.getMyJobPostingById(id);
        return ResponseEntity.ok(job);
    }

    // ========== ADMIN ENDPOINTS ==========

    @GetMapping("/admin")
    public ResponseEntity<Page<JobPosting>> getAllJobsAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("postedDate").descending());
        Page<JobPosting> jobs = jobPostingService.getAllJobPostings(pageable);
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/admin/{id}/toggle-status")
    public ResponseEntity<JobPosting> toggleJobStatus(@PathVariable Integer id) {
        JobPosting job = jobPostingService.toggleJobPostingStatus(id);
        return ResponseEntity.ok(job);
    }
}