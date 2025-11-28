package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTypeRepo extends JpaRepository<JobType,String> {
}
