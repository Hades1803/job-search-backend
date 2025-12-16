// RankRepo.java
package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankRepo extends JpaRepository<Rank, String> {
    Optional<Rank> findByName(String name);
    List<Rank> findAllByOrderByNameAsc();
}