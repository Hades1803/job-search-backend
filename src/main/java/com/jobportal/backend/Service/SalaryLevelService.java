// SalaryLevelService.java
package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.SalaryLevel;

import java.util.List;

public interface SalaryLevelService {
    List<SalaryLevel> getAll();
    SalaryLevel getById(String id);
    SalaryLevel create(ReferenceRequest request);
    SalaryLevel update(String id, ReferenceRequest request);
    void delete(String id);
}