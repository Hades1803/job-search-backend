// RankController.java
package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Rank;
import com.jobportal.backend.Service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranks")
@RequiredArgsConstructor
public class RankController {

    private final RankService rankService;

    @GetMapping
    public ResponseEntity<List<Rank>> getAll() {
        return ResponseEntity.ok(rankService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rank> getById(@PathVariable String id) {
        return ResponseEntity.ok(rankService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Rank> create(@RequestBody ReferenceRequest request) {
        Rank created = rankService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rank> update(@PathVariable String id, @RequestBody ReferenceRequest request) {
        return ResponseEntity.ok(rankService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rankService.delete(id);
        return ResponseEntity.noContent().build();
    }
}