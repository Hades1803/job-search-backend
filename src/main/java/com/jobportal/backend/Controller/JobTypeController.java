// JobTypeController.java
package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.JobType;
import com.jobportal.backend.Service.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-types")
@RequiredArgsConstructor
public class JobTypeController {

    private final JobTypeService jobTypeService;

    @GetMapping
    public ResponseEntity<List<JobType>> getAll() {
        return ResponseEntity.ok(jobTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobType> getById(@PathVariable String id) {
        return ResponseEntity.ok(jobTypeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<JobType> create(@RequestBody ReferenceRequest request) {
        JobType created = jobTypeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobType> update(@PathVariable String id, @RequestBody ReferenceRequest request) {
        return ResponseEntity.ok(jobTypeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        jobTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}