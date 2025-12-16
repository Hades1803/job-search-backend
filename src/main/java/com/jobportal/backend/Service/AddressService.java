package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAll();
    Address getById(String id);
    Address create(ReferenceRequest request);
    Address update(String id, ReferenceRequest request);
    void delete(String id);
}