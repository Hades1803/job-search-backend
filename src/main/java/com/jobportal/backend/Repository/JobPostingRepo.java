package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepo extends JpaRepository<JobPosting,Integer> {
}
