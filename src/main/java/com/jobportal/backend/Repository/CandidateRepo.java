package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CandidateRepo extends JpaRepository<Candidate,Integer> {


    Optional<Candidate> findByAccountId(Integer accountId);
    Optional<Candidate> findByAccountEmail(String email);
    @Query("SELECT c FROM Candidate c WHERE c.account.role.name = com.jobportal.backend.Entity.RoleType.CANDIDATE")
    List<Candidate> findAllCandidatesOnly();
}
