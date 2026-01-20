package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepo extends JpaRepository<Employer, Integer> {
    Optional<Employer> findByAccountId(Integer accountId);

    @Query("SELECT e FROM Employer e WHERE e.account.role.name = com.jobportal.backend.Entity.RoleType.EMPLOYER")
    List<Employer> findAllEmployersOnly();
}