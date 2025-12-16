package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {


    Optional<Candidate> findByAccountId(Integer accountId);
    Optional<Candidate> findByAccountEmail(String email);
}
