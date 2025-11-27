package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepo extends JpaRepository<Resume,Integer> {
}
