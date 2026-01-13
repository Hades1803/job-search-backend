package com.jobportal.backend.Controller;

import com.jobportal.backend.Entity.*;
import com.jobportal.backend.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PublicController {

    private final MajorService majorService;
    private final JobTypeService jobTypeService;
    private final SalaryLevelService salaryLevelService;
    private final RankService rankService;
    private final AddressService addressService;

    @GetMapping("/majors")
    public ResponseEntity<List<Major>> getAllMajors() {
        return ResponseEntity.ok(majorService.getAll());
    }

    @GetMapping("/job-types")
    public ResponseEntity<List<JobType>> getAllJobTypes() {
        return ResponseEntity.ok(jobTypeService.getAll());
    }

    @GetMapping("/salary-levels")
    public ResponseEntity<List<SalaryLevel>> getAllSalaryLevels() {
        return ResponseEntity.ok(salaryLevelService.getAll());
    }

    @GetMapping("/ranks")
    public ResponseEntity<List<Rank>> getAllRanks() {
        return ResponseEntity.ok(rankService.getAll());
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAll());
    }
}