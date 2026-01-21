package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Dto.JobApplicationResponse;
import com.jobportal.backend.Dto.MyApplicationResponse;
import com.jobportal.backend.Dto.ResumeResponse;
import com.jobportal.backend.Entity.*;
import com.jobportal.backend.Repository.*;
import com.jobportal.backend.Service.ApplicationService;
import com.jobportal.backend.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepo applicationRepository;
    private final CandidateRepo candidateRepository;
    private final JobPostingRepo jobPostingRepository;
    private final ResumeRepo resumeRepository;
    private final FileService fileService;

    @Override
    public Application applyJob(ApplyJobRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Candidate candidate = candidateRepository.findByAccountEmail(email)
                .orElseThrow(() -> new RuntimeException("Candidate không tồn tại"));

        if (applicationRepository.existsByCandidate_IdAndJobPosting_Id(
                candidate.getId(),
                request.getJobPostingId())) {
            throw new RuntimeException("Bạn đã ứng tuyển công việc này rồi");
        }

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobPostingId())
                .orElseThrow(() -> new RuntimeException("Job không tồn tại"));

        Application application = new Application();
        application.setCandidate(candidate);
        application.setJobPosting(jobPosting);
        application.setApplyDate(LocalDateTime.now());
        application.setStatus(ApplicationStatus.PENDING);

        if (request.getResumeId() != null) {
            // Sử dụng resume từ DB
            Resume resume = resumeRepository.findById(request.getResumeId())
                    .orElseThrow(() -> new RuntimeException("Resume không tồn tại"));

            application.setResume(resume);
            application.setResumeType(Application.ResumeType.DB_RESUME);

        } else if (request.getCvFile() != null && !request.getCvFile().isEmpty()) {
            String cvUrl;
            try {
                cvUrl = fileService.uploadCv(request.getCvFile());
            } catch (IOException e) {
                throw new RuntimeException("Upload CV thất bại");
            }

            application.setUploadedCVPath(cvUrl);
            application.setResumeType(Application.ResumeType.UPLOADED_FILE);

        } else if (request.getResumeLink() != null && !request.getResumeLink().isBlank()) {
            // Sử dụng link CV
            application.setResumeLink(request.getResumeLink());
            application.setResumeType(Application.ResumeType.LINK_ONLY);

        } else {
            throw new RuntimeException("Vui lòng chọn ít nhất 1 hình thức gửi CV");
        }

        return applicationRepository.save(application);
    }



    @Override
    public List<Application> getApplicationsByCandidate(Integer candidateId) {
        return applicationRepository.findByCandidate_Id(candidateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponse> getApplicationsByJob(Integer jobId) {

        return applicationRepository
                .findByJobPostingWithCandidate(jobId)
                .stream()
                .map(app -> {
                    JobApplicationResponse dto = new JobApplicationResponse();
                    dto.setApplicationId(app.getId());
                    dto.setCandidateId(app.getCandidate().getId());
                    dto.setCandidateName(app.getCandidate().getName());
                    dto.setCandidateEmail(app.getCandidate().getAccount().getEmail());
                    dto.setResumeType(app.getResumeType().name());
                    dto.setStatus(app.getStatus().name());
                    dto.setApplyDate(app.getApplyDate());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<MyApplicationResponse> getMyApplications() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Candidate candidate = candidateRepository.findByAccountEmail(email)
                .orElseThrow(() -> new RuntimeException("Candidate không tồn tại"));

        List<Application> applications =
                applicationRepository.findMyApplications(candidate.getId());

        return applications.stream().map(app -> {
            MyApplicationResponse dto = new MyApplicationResponse();
            dto.setApplicationId(app.getId());
            dto.setJobId(app.getJobPosting().getId());
            dto.setJobTitle(app.getJobPosting().getJobTitle());
            dto.setCompanyName(
                    app.getJobPosting().getEmployer().getEmployerName()
            );
            dto.setResumeType(app.getResumeType().name());
            dto.setStatus(app.getStatus().name());
            dto.setApplyDate(app.getApplyDate());
            return dto;
        }).toList();
    }

    @Override
    public void updateApplicationStatus(
            Integer applicationId,
            ApplicationStatus status
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application không tồn tại"));


        Employer employer = application.getJobPosting().getEmployer();
        if (!employer.getAccount().getEmail().equals(email)) {
            throw new RuntimeException("Bạn không có quyền cập nhật trạng thái ứng tuyển này");
        }

        application.setStatus(status);
        applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeResponse getResumeByApplicationId(Integer applicationId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application không tồn tại"));

        Employer employer = application.getJobPosting().getEmployer();
        if (!employer.getAccount().getEmail().equals(email)) {
            throw new RuntimeException("Bạn không có quyền xem CV của ứng viên này");
        }

        if (application.getResumeType() == null || application.getResume() == null) {
            throw new RuntimeException("Resume chưa được đính kèm");
        }

        Resume resume = application.getResume();

        ResumeResponse response = new ResumeResponse();
        response.setId(resume.getId());
        response.setResumeName(resume.getResumeName());
        response.setTemplateCode(resume.getTemplateCode());
        response.setContent(resume.getContent());
        response.setUpdatedAt(resume.getUpdatedAt());

        return response;
    }






}
