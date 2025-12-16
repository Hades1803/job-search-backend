// SalaryLevelRepo.java
package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.SalaryLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryLevelRepo extends JpaRepository<SalaryLevel, String> {
    Optional<SalaryLevel> findByName(String name);
    List<SalaryLevel> findAllByOrderByNameAsc();
}