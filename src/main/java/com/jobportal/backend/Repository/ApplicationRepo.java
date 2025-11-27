package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepo extends JpaRepository<Application,Integer> {
}
