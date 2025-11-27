package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.SalaryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryLevelRepo extends JpaRepository<SalaryLevel,String> {
}
