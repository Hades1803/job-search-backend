package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepo extends JpaRepository<Employer,Integer> {
}
