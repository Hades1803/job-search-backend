package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.JobPostingRequest;
import com.jobportal.backend.Dto.JobPostingResponse;
import com.jobportal.backend.Entity.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobPostingService {
    // Employer
    JobPosting createJobPosting(JobPostingRequest request);
    JobPosting updateJobPosting(Integer id, JobPostingRequest request);
    void deleteJobPosting(Integer id);
    List<JobPosting> getMyJobPostings();
    JobPosting getMyJobPostingById(Integer id);

    // Public
    Page<JobPostingResponse> getAllActiveJobPostings(Pageable pageable);
    JobPostingResponse getJobPostingById(Integer id);

    // Thay đổi từ Integer thành String cho các ID
    Page<JobPostingResponse> searchJobPostings(String keyword,
                                               String majorId,
                                               String jobTypeId,
                                               String addressId,
                                               Pageable pageable);

    // Admin
    Page<JobPosting> getAllJobPostings(Pageable pageable);
    JobPosting toggleJobPostingStatus(Integer id);
}