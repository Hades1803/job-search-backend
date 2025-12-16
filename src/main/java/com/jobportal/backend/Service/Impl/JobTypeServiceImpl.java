// JobTypeServiceImpl.java
package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.JobType;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.JobTypeRepo;
import com.jobportal.backend.Service.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepo jobTypeRepo;

    @Override
    public List<JobType> getAll() {
        return jobTypeRepo.findAllByOrderByNameAsc();
    }

    @Override
    public JobType getById(String id) {
        return jobTypeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại công việc với ID: " + id));
    }

    @Override
    public JobType create(ReferenceRequest request) {
        if (jobTypeRepo.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException("ID loại công việc đã tồn tại: " + request.getId());
        }

        if (jobTypeRepo.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên loại công việc đã tồn tại: " + request.getName());
        }

        JobType jobType = new JobType();
        jobType.setId(request.getId());
        jobType.setName(request.getName());

        return jobTypeRepo.save(jobType);
    }

    @Override
    public JobType update(String id, ReferenceRequest request) {
        JobType jobType = getById(id);

        if (!jobType.getName().equals(request.getName())) {
            if (jobTypeRepo.findByName(request.getName()).isPresent()) {
                throw new IllegalArgumentException("Tên loại công việc đã tồn tại: " + request.getName());
            }
        }

        jobType.setName(request.getName());
        return jobTypeRepo.save(jobType);
    }

    @Override
    public void delete(String id) {
        JobType jobType = getById(id);
        jobTypeRepo.delete(jobType);
    }
}