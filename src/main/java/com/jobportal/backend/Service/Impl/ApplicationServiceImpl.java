package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Entity.*;
import com.jobportal.backend.Repository.*;
import com.jobportal.backend.Service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo applicationRepository;
    private final CandidateRepo candidateRepository;
    private final JobPostingRepo jobPostingRepository;
    private final ResumeRepo resumeRepository;

    @Override
    public Application applyJob(ApplyJobRequest request) {

        if (applicationRepository.existsByCandidate_IdAndJobPosting_Id(
                request.getCandidateId(),
                request.getJobPostingId())) {
            throw new RuntimeException("Bạn đã ứng tuyển công việc này rồi");
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate không tồn tại"));

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow(() -> new RuntimeException("Job không tồn tại"));

        Resume resume = null;
        if (request.getResumeId() != null) {
            resume = resumeRepository.findById(request.getResumeId())
                    .orElseThrow(() -> new RuntimeException("Resume không tồn tại"));
        }

        Application application = new Application();
        application.setCandidate(candidate);
        application.setJobPosting(jobPosting);
        application.setResume(resume);
        application.setResumeLink(request.getResumeLink());
        application.setApplyDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    @Override
    public List<Application> getApplicationsByCandidate(Integer candidateId) {
        return applicationRepository.findByCandidate_Id(candidateId);
    }

    @Override
    public List<Application> getApplicationsByJob(Integer jobId) {
        return applicationRepository.findByJobPosting_Id(jobId);
    }
}
