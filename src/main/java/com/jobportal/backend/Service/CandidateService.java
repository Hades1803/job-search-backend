package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.CandidateProfileRequest;
import com.jobportal.backend.Entity.Candidate;

public interface CandidateService {

    Candidate getMyProfile();

    Candidate updateMyProfile(CandidateProfileRequest request);
}
