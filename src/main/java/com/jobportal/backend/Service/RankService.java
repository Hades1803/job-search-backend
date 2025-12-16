// RankService.java
package com.jobportal.backend.Service;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Rank;

import java.util.List;

public interface RankService {
    List<Rank> getAll();
    Rank getById(String id);
    Rank create(ReferenceRequest request);
    Rank update(String id, ReferenceRequest request);
    void delete(String id);
}