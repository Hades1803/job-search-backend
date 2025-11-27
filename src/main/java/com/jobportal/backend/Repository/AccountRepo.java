package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,Integer> {

}
