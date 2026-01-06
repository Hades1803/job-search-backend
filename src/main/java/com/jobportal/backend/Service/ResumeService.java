package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ResumeRequest;
import com.jobportal.backend.Entity.Resume;

import java.util.List;

public interface ResumeService {

    Resume createResume(ResumeRequest request);

    Resume updateResume(Integer resumeId, ResumeRequest request);

    Resume getResumeById(Integer resumeId);

    List<Resume> getMyResumes();

    void deleteResume(Integer resumeId);
}