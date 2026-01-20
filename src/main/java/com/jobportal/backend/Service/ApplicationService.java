package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Dto.JobApplicationResponse;
import com.jobportal.backend.Dto.MyApplicationResponse;
import com.jobportal.backend.Entity.Application;
import com.jobportal.backend.Entity.ApplicationStatus;

import java.util.List;

public interface ApplicationService {

    Application applyJob(ApplyJobRequest request);

    List<Application> getApplicationsByCandidate(Integer candidateId);

    List<JobApplicationResponse> getApplicationsByJob(Integer jobId);

    List<MyApplicationResponse> getMyApplications();
    void updateApplicationStatus(Integer applicationId, ApplicationStatus status);
    String getResumeByApplicationId(Integer applicationId);
}
