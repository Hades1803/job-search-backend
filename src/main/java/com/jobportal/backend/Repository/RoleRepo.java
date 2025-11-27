package com.jobportal.backend.Repository;


import com.jobportal.backend.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
