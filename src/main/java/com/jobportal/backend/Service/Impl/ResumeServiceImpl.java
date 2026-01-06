package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ResumeRequest;
import com.jobportal.backend.Entity.*;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.*;
import com.jobportal.backend.Service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepo resumeRepository;
    private final CandidateRepo candidateRepo;
    private final AccountRepo accountRepo;
    private final MajorRepo majorRepository;
    private final JobTypeRepo jobTypeRepository;
    private final RankRepo rankRepository;

    @Override
    public Resume createResume(ResumeRequest request) {
        Candidate candidate = getCurrentCandidate();

        Resume resume = new Resume();
        resume.setCandidate(candidate);
        mapData(resume, request);

        return resumeRepository.save(resume);
    }

    @Override
    public Resume updateResume(Integer resumeId, ResumeRequest request) {
        Resume resume = getResumeOwnedByCandidate(resumeId);
        mapData(resume, request);
        return resumeRepository.save(resume);
    }

    @Override
    public Resume getResumeById(Integer resumeId) {
        return getResumeOwnedByCandidate(resumeId);
    }

    @Override
    public List<Resume> getMyResumes() {
        Candidate candidate = getCurrentCandidate();
        return resumeRepository.findByCandidate_Id(candidate.getId());
    }

    @Override
    public void deleteResume(Integer resumeId) {
        Resume resume = getResumeOwnedByCandidate(resumeId);
        resumeRepository.delete(resume);
    }

    // ================== PRIVATE ==================

    private void mapData(Resume resume, ResumeRequest request) {
        resume.setResumeName(request.getResumeName());
        resume.setCareerObjective(request.getCareerObjective());
        resume.setExperience(request.getExperience());
        resume.setSkills(request.getSkills());
        resume.setEducation(request.getEducation());
        resume.setSoftSkills(request.getSoftSkills());
        resume.setAwards(request.getAwards());

        if (request.getMajorId() != null) {
            resume.setMajor(majorRepository.findById(request.getMajorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Major không tồn tại")));
        }

        if (request.getJobTypeId() != null) {
            resume.setJobType(jobTypeRepository.findById(request.getJobTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("JobType không tồn tại")));
        }

        if (request.getRankId() != null) {
            resume.setRank(rankRepository.findById(request.getRankId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rank không tồn tại")));
        }
    }

    private Resume getResumeOwnedByCandidate(Integer resumeId) {
        Candidate candidate = getCurrentCandidate();

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume không tồn tại"));

        if (!resume.getCandidate().getId().equals(candidate.getId())) {
            throw new RuntimeException("Bạn không có quyền truy cập resume này");
        }

        return resume;
    }

    private Candidate getCurrentCandidate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Account account = accountRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account không tồn tại"));

        return candidateRepo.findById(account.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate chưa có hồ sơ"));
    }
}
