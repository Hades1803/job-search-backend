package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.CandidateProfileRequest;
import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Candidate;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.CandidateRepo;
import com.jobportal.backend.Service.CandidateService;
import com.jobportal.backend.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final AccountRepo accountRepo;
    private final FileService fileService;

    @Value("${project.image}")
    private String imagePath;

    @Override
    public Candidate updateMyProfile(CandidateProfileRequest request) throws IOException {
        Account account = getCurrentAccount();

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }

        Candidate candidate = candidateRepo.findById(account.getId())
                .orElseGet(() -> {
                    Candidate newCandidate = new Candidate();
                    newCandidate.setAccount(account);
                    return newCandidate;
                });

        candidate.setName(request.getName().trim());
        candidate.setPhone(request.getPhone());
        candidate.setGender(request.getGender());
        candidate.setBirthDate(request.getBirthDate());
        candidate.setAddress(request.getAddress());

        // Upload avatar
        MultipartFile avatarFile = request.getAvatarFile();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String fileName = fileService.uploadImage(imagePath, avatarFile);
            candidate.setAvatar("/uploads/images/" + fileName);
        }

        // Upload cover image
        MultipartFile coverFile = request.getCoverImageFile();
        if (coverFile != null && !coverFile.isEmpty()) {
            String fileName = fileService.uploadImage(imagePath, coverFile);
            candidate.setCoverImage("/uploads/images/" + fileName);
        }

        return candidateRepo.save(candidate);
    }

    @Override
    public Candidate getMyProfile() {
        return null;
    }

    private Account getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account không tồn tại"));
    }
}