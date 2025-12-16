// MajorRepo.java
package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepo extends JpaRepository<Major, String> {
    Optional<Major> findByName(String name);
    List<Major> findAllByOrderByNameAsc();
}