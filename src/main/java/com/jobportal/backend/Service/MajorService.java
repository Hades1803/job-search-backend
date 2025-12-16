// MajorService.java
package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Major;

import java.util.List;

public interface MajorService {
    List<Major> getAll();
    Major getById(String id);
    Major create(ReferenceRequest request);
    Major update(String id, ReferenceRequest request);
    void delete(String id);
}