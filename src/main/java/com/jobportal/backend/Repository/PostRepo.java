package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post,Integer> {
}
