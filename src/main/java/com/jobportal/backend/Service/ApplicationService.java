package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ApplyJobRequest;
import com.jobportal.backend.Dto.MyApplicationResponse;
import com.jobportal.backend.Entity.Application;

import java.util.List;

public interface ApplicationService {

    Application applyJob(ApplyJobRequest request);

    List<Application> getApplicationsByCandidate(Integer candidateId);

    List<Application> getApplicationsByJob(Integer jobId);

    List<MyApplicationResponse> getMyApplications();
}
