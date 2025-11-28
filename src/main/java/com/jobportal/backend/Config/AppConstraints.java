package com.jobportal.backend.Config;

public class AppConstraints {

    // Public endpoints - không cần authentication
    public static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/api/auth/verify-email/**",
            "/api/public/**"
    };

    // Admin only endpoints
    public static final String[] ADMIN_ENDPOINTS = {
            "/api/admin/**",
            "/api/users/manage/**",
            "/api/roles/**"
    };

    // Employer endpoints
    public static final String[] EMPLOYER_ENDPOINTS = {
            "/api/employer/jobs/**",
            "/api/employer/applications/**",
            "/api/employer/profile/**"
    };

    // Job seeker endpoints
    public static final String[] JOB_SEEKER_ENDPOINTS = {
            "/api/job-seeker/applications/**",
            "/api/job-seeker/profile/**",
            "/api/job-seeker/saved-jobs/**"
    };

    // Common authenticated endpoints (all logged-in users)
    public static final String[] AUTHENTICATED_ENDPOINTS = {
            "/api/profile/**",
            "/api/notifications/**"
    };

    // JWT settings
    public static final long JWT_EXPIRATION = 1000 * 60 * 60; // 1 hour
    public static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    // Validation constraints
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MAX_EMAIL_LENGTH = 255;

    // Role names (nên khớp với enum Role)
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_EMPLOYER = "EMPLOYER";
    public static final String ROLE_JOB_SEEKER = "JOB_SEEKER";

    // Private constructor to prevent instantiation
    private AppConstraints() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}