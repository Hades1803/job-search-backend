package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Entity.RoleType;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.EmployerRepo;
import com.jobportal.backend.Service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepo employerRepository;
    private final AccountRepo accountRepo;

    @Override
    @Transactional
    public Employer createEmployer(Employer employer, Account currentAccount) {
        // Lấy account đã được managed từ database
        Account managedAccount = accountRepo.findById(currentAccount.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Kiểm tra role
        if (managedAccount.getRole() == null || managedAccount.getRole().getName() != RoleType.EMPLOYER) {
            throw new RuntimeException("Only EMPLOYER can create employer profile");
        }

        // Kiểm tra đã có employer profile chưa
        if (managedAccount.getEmployer() != null) {
            throw new RuntimeException("Employer profile already exists");
        }

        // QUAN TRỌNG: Set account cho employer
        // Vì dùng @MapsId, nên ID của employer sẽ tự động = ID của account
        employer.setAccount(managedAccount);

        // Lưu employer
        Employer savedEmployer = employerRepository.save(employer);

        // Cập nhật bên account (nếu có quan hệ hai chiều)
        managedAccount.setEmployer(savedEmployer);
        accountRepo.save(managedAccount);

        return savedEmployer;
    }

    @Override
    @Transactional
    public Employer updateEmployer(Employer employer, Account currentAccount) {
        // Tìm employer theo account ID (vì dùng shared primary key)
        Employer existing = employerRepository.findById(currentAccount.getId())
                .orElseThrow(() -> new RuntimeException("Employer profile not found"));

        // Cập nhật các trường
        existing.setEmployerName(employer.getEmployerName());
        existing.setRepresentativeName(employer.getRepresentativeName());
        existing.setRepresentativePosition(employer.getRepresentativePosition());
        existing.setPhone(employer.getPhone());
        existing.setCoverImage(employer.getCoverImage());
        existing.setLogoImage(employer.getLogoImage());
        existing.setScale(employer.getScale());
        existing.setDescription(employer.getDescription());
        existing.setAddress(employer.getAddress());
        existing.setWebsite(employer.getWebsite());

        return employerRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Employer getMyEmployer(Account currentAccount) {
        return employerRepository.findById(currentAccount.getId())
                .orElseThrow(() -> new RuntimeException("Employer profile not found"));
    }
}