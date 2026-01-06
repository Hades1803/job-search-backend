package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepo extends JpaRepository<Resume,Integer> {
    List<Resume> findByCandidate_Id(Integer candidateId);
}
