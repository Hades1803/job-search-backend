package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, Integer> {
    Optional<Employer> findByAccountId(Integer accountId);
}