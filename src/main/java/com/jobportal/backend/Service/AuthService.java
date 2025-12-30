package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.AuthRequest;
import com.jobportal.backend.Dto.AuthResponse;
import com.jobportal.backend.Dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    AuthResponse login(AuthRequest request);
}
