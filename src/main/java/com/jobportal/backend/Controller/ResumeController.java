package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ResumeRequest;
import com.jobportal.backend.Entity.Resume;
import com.jobportal.backend.Service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public Resume createResume(@RequestBody ResumeRequest request) {
        return resumeService.createResume(request);
    }

    @PutMapping("/{id}")
    public Resume updateResume(@PathVariable Integer id,
                               @RequestBody ResumeRequest request) {
        return resumeService.updateResume(id, request);
    }

    @GetMapping("/{id}")
    public Resume getResume(@PathVariable Integer id) {
        return resumeService.getResumeById(id);
    }

    @GetMapping
    public List<Resume> getMyResumes() {
        return resumeService.getMyResumes();
    }

    @DeleteMapping("/{id}")
    public void deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
    }
}
