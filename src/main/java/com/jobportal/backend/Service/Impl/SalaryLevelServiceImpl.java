// SalaryLevelServiceImpl.java
package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.SalaryLevel;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.SalaryLevelRepo;
import com.jobportal.backend.Service.SalaryLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryLevelServiceImpl implements SalaryLevelService {

    private final SalaryLevelRepo salaryLevelRepo;

    @Override
    public List<SalaryLevel> getAll() {
        return salaryLevelRepo.findAllByOrderByNameAsc();
    }

    @Override
    public SalaryLevel getById(String id) {
        return salaryLevelRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy mức lương với ID: " + id));
    }

    @Override
    public SalaryLevel create(ReferenceRequest request) {
        if (salaryLevelRepo.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException("ID mức lương đã tồn tại: " + request.getId());
        }

        if (salaryLevelRepo.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên mức lương đã tồn tại: " + request.getName());
        }

        SalaryLevel salaryLevel = new SalaryLevel();
        salaryLevel.setId(request.getId());
        salaryLevel.setName(request.getName());

        return salaryLevelRepo.save(salaryLevel);
    }

    @Override
    public SalaryLevel update(String id, ReferenceRequest request) {
        SalaryLevel salaryLevel = getById(id);

        if (!salaryLevel.getName().equals(request.getName())) {
            if (salaryLevelRepo.findByName(request.getName()).isPresent()) {
                throw new IllegalArgumentException("Tên mức lương đã tồn tại: " + request.getName());
            }
        }

        salaryLevel.setName(request.getName());
        return salaryLevelRepo.save(salaryLevel);
    }

    @Override
    public void delete(String id) {
        SalaryLevel salaryLevel = getById(id);
        salaryLevelRepo.delete(salaryLevel);
    }
}