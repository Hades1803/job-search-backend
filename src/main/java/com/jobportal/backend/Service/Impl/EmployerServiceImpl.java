package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.EmployerProfileRequest;
import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Entity.RoleType;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.EmployerRepo;
import com.jobportal.backend.Service.EmployerService;
import com.jobportal.backend.Service.FileService;
import com.jobportal.backend.Utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepo employerRepository;
    private final AccountRepo accountRepo;
    private final FileService fileService;
    @Value("${project.image}")
    private String imagePath;

    @Override
    public Employer updateEmployer(EmployerProfileRequest request) throws IOException {

        Account currentAccount = getCurrentAccount();

        Employer existing = employerRepository.findById(currentAccount.getId())
                .orElseThrow(() -> new RuntimeException("Employer profile not found"));

        existing.setEmployerName(request.getEmployerName());
        existing.setRepresentativeName(request.getRepresentativeName());
        existing.setRepresentativePosition(request.getRepresentativePosition());
        existing.setPhone(request.getPhone());
        existing.setScale(request.getScale());
        existing.setDescription(request.getDescription());
        existing.setAddress(request.getAddress());
        existing.setWebsite(request.getWebsite());

        if (request.getLogoFile() != null && !request.getLogoFile().isEmpty()) {
            String logoName = fileService.uploadImage(imagePath, request.getLogoFile());
            existing.setLogoImage("/uploads/images/" + logoName);
        }

        if (request.getCoverFile() != null && !request.getCoverFile().isEmpty()) {
            String coverName = fileService.uploadImage(imagePath, request.getCoverFile());
            existing.setCoverImage("/uploads/images/" + coverName);
        }

        return employerRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Employer getMyEmployer() {
        Account currentAccount = getCurrentAccount();
        return employerRepository.findById(currentAccount.getId())
                .orElseThrow(() -> new RuntimeException("Employer profile not found"));
    }

    private Account getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employer> getAllEmployersForAdmin() {
        return employerRepository.findAllEmployersOnly();
    }

}
