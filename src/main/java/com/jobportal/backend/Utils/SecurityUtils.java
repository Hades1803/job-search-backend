package com.jobportal.backend.Utils;

import com.jobportal.backend.Config.AppConstraints;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityUtils {

    /**
     * Lấy email của user hiện tại
     */
    public String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : null;
    }

    /**
     * Kiểm tra user hiện tại có role cụ thể không
     */
    public boolean hasRole(String roleName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return false;

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String targetRole = "ROLE_" + roleName.toUpperCase();

        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(targetRole));
    }

    /**
     * Kiểm tra nếu là ADMIN
     */
    public boolean isAdmin() {
        return hasRole(AppConstraints.ROLE_ADMIN);
    }

    /**
     * Kiểm tra nếu là EMPLOYER
     */
    public boolean isEmployer() {
        return hasRole(AppConstraints.ROLE_EMPLOYER);
    }

    /**
     * Kiểm tra nếu là CANDIDATE (JOB_SEEKER)
     */
    public boolean isCandidate() {
        // Trong AppConstraints, ROLE_JOB_SEEKER = "CANDIDATE"
        return hasRole(AppConstraints.ROLE_CANDIDATE);
    }

    /**
     * Lấy Authentication object
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Kiểm tra user hiện tại có phải là owner không (dựa trên accountId)
     */
    public boolean isOwner(Integer accountId) {
        // Cần implement logic so sánh accountId với current user
        // Tùy vào cách bạn lưu accountId trong token/user details
        return false;
    }
}