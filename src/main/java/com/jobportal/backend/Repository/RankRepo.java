package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepo extends JpaRepository<Rank,String> {
}
