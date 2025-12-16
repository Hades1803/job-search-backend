// SalaryLevelController.java
package com.jobportal.backend.Controller;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.SalaryLevel;
import com.jobportal.backend.Service.SalaryLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary-levels")
@RequiredArgsConstructor
public class SalaryLevelController {

    private final SalaryLevelService salaryLevelService;

    @GetMapping
    public ResponseEntity<List<SalaryLevel>> getAll() {
        return ResponseEntity.ok(salaryLevelService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryLevel> getById(@PathVariable String id) {
        return ResponseEntity.ok(salaryLevelService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SalaryLevel> create(@RequestBody ReferenceRequest request) {
        SalaryLevel created = salaryLevelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryLevel> update(@PathVariable String id, @RequestBody ReferenceRequest request) {
        return ResponseEntity.ok(salaryLevelService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        salaryLevelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}