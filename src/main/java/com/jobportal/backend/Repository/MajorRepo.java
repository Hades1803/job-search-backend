package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepo extends JpaRepository<Major,String> {
}
