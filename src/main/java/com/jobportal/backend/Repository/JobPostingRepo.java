// JobPostingRepo.java
package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobPostingRepo extends JpaRepository<JobPosting, Integer>,
        JpaSpecificationExecutor<JobPosting> {

    // For employer
    List<JobPosting> findByEmployerId(Integer employerId);

    // For public - chỉ lấy job active và chưa hết hạn
    Page<JobPosting> findByStatusTrueAndExpirationDateAfter(
            LocalDateTime expirationDate, Pageable pageable);

    @Query("SELECT j FROM JobPosting j JOIN j.employer e")
    Page<JobPosting> findAllWithEmployer(Pageable pageable);

}