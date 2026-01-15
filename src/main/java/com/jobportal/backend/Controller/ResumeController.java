package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ResumeRequest;
import com.jobportal.backend.Dto.ResumeResponse;
import com.jobportal.backend.Entity.Resume;
import com.jobportal.backend.Service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/resumes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResumeResponse createResume(@RequestBody ResumeRequest request) {
        Resume resume = resumeService.createResume(request);
        return toResponse(resume);
    }

    @PutMapping("/{id}")
    public ResumeResponse updateResume(@PathVariable Integer id,
                                       @RequestBody ResumeRequest request) {
        Resume resume = resumeService.updateResume(id, request);
        return toResponse(resume);
    }

    @GetMapping("/{id}")
    public ResumeResponse getResume(@PathVariable Integer id) {
        Resume resume = resumeService.getResumeById(id);
        return toResponse(resume);
    }

    @GetMapping
    public List<ResumeResponse> getMyResumes() {
        return resumeService.getMyResumes()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
    }

    // ================== PRIVATE ==================

    private ResumeResponse toResponse(Resume resume) {
        ResumeResponse res = new ResumeResponse();
        res.setId(resume.getId());
        res.setResumeName(resume.getResumeName());
        res.setTemplateCode(resume.getTemplateCode());
        res.setContent(resume.getContent());
        res.setUpdatedAt(resume.getUpdatedAt());
        return res;
    }
}
