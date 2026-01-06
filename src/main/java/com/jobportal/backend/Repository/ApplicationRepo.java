package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application,Integer> {

    List<Application> findByCandidate_Id(Integer candidateId);

    // Lấy danh sách ứng tuyển cho 1 job
    List<Application> findByJobPosting_Id(Integer jobId);

    // Check đã ứng tuyển chưa
    boolean existsByCandidate_IdAndJobPosting_Id(Integer candidateId, Integer jobId);
}
