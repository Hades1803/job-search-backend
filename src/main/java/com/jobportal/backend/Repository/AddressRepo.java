package com.jobportal.backend.Repository;

import com.jobportal.backend.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,String> {
}
