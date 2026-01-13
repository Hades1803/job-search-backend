// MajorController.java
package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Major;
import com.jobportal.backend.Service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MajorController {

    private final MajorService majorService;

    @GetMapping
    public ResponseEntity<List<Major>> getAll() {
        return ResponseEntity.ok(majorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Major> getById(@PathVariable String id) {
        return ResponseEntity.ok(majorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Major> create(@RequestBody ReferenceRequest request) {
        Major created = majorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Major> update(@PathVariable String id, @RequestBody ReferenceRequest request) {
        return ResponseEntity.ok(majorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        majorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}