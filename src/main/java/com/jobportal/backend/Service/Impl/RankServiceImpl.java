// RankServiceImpl.java
package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Rank;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.RankRepo;
import com.jobportal.backend.Service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RankServiceImpl implements RankService {

    private final RankRepo rankRepo;

    @Override
    public List<Rank> getAll() {
        return rankRepo.findAllByOrderByNameAsc();
    }

    @Override
    public Rank getById(String id) {
        return rankRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cấp bậc với ID: " + id));
    }

    @Override
    public Rank create(ReferenceRequest request) {
        if (rankRepo.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException("ID cấp bậc đã tồn tại: " + request.getId());
        }

        if (rankRepo.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên cấp bậc đã tồn tại: " + request.getName());
        }

        Rank rank = new Rank();
        rank.setId(request.getId());
        rank.setName(request.getName());

        return rankRepo.save(rank);
    }

    @Override
    public Rank update(String id, ReferenceRequest request) {
        Rank rank = getById(id);

        if (!rank.getName().equals(request.getName())) {
            if (rankRepo.findByName(request.getName()).isPresent()) {
                throw new IllegalArgumentException("Tên cấp bậc đã tồn tại: " + request.getName());
            }
        }

        rank.setName(request.getName());
        return rankRepo.save(rank);
    }

    @Override
    public void delete(String id) {
        Rank rank = getById(id);
        rankRepo.delete(rank);
    }
}