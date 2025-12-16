package com.jobportal.backend.Repository;


import com.jobportal.backend.Entity.ForgotPassword;
import com.jobportal.backend.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword, Long> {
    Optional<ForgotPassword> findByAccount(Account account);
    Optional<ForgotPassword> findByOtp(Integer otp);
    void deleteByAccount(Account account);
}