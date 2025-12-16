// JobTypeRepo.java
package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobTypeRepo extends JpaRepository<JobType, String> {
    Optional<JobType> findByName(String name);
    List<JobType> findAllByOrderByNameAsc();
}