
package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.JobType;

import java.util.List;

public interface JobTypeService {
    List<JobType> getAll();
    JobType getById(String id);
    JobType create(ReferenceRequest request);
    JobType update(String id, ReferenceRequest request);
    void delete(String id);
}