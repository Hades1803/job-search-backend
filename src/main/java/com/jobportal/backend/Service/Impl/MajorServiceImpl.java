// MajorServiceImpl.java
package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Major;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.MajorRepo;
import com.jobportal.backend.Service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MajorServiceImpl implements MajorService {

    private final MajorRepo majorRepo;

    @Override
    public List<Major> getAll() {
        return majorRepo.findAllByOrderByNameAsc();
    }

    @Override
    public Major getById(String id) {
        return majorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ngành nghề với ID: " + id));
    }

    @Override
    public Major create(ReferenceRequest request) {
        // Kiểm tra ID đã tồn tại chưa
        if (majorRepo.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException("ID ngành nghề đã tồn tại: " + request.getId());
        }

        // Kiểm tra tên đã tồn tại chưa
        if (majorRepo.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên ngành nghề đã tồn tại: " + request.getName());
        }

        Major major = new Major();
        major.setId(request.getId());
        major.setName(request.getName());

        return majorRepo.save(major);
    }

    @Override
    public Major update(String id, ReferenceRequest request) {
        Major major = getById(id);

        // Kiểm tra tên mới có trùng với ngành khác không
        if (!major.getName().equals(request.getName())) {
            if (majorRepo.findByName(request.getName()).isPresent()) {
                throw new IllegalArgumentException("Tên ngành nghề đã tồn tại: " + request.getName());
            }
        }

        major.setName(request.getName());
        return majorRepo.save(major);
    }

    @Override
    public void delete(String id) {
        Major major = getById(id);
        majorRepo.delete(major);
    }
}