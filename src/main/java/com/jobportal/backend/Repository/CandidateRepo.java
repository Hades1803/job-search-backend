package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {
}
