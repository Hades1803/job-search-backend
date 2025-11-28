package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Integer> {
    Optional<Account> findByEmail(String email);
}
