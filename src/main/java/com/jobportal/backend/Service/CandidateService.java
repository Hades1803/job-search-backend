package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.CandidateProfileRequest;
import com.jobportal.backend.Entity.Candidate;

import java.io.IOException;

public interface CandidateService {



    Candidate updateMyProfile(CandidateProfileRequest request) throws IOException;
    Candidate getMyProfile();
}
