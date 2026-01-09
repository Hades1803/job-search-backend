package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.CandidateProfileRequest;
import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Entity.Candidate;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.AccountRepo;
import com.jobportal.backend.Repository.CandidateRepo;
import com.jobportal.backend.Service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepo candidateRepo;
    private final AccountRepo accountRepo;

    @Override
    public Candidate getMyProfile() {
        Account account = getCurrentAccount();

        // CHỈ lấy profile nếu đã có, không tự tạo
        return candidateRepo.findById(account.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bạn chưa có hồ sơ ứng viên. Vui lòng tạo hồ sơ trước."
                ));
    }

    @Override
    public Candidate updateMyProfile(CandidateProfileRequest request) {
        Account account = getCurrentAccount();

        // Validate tên bắt buộc
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống");
        }

        // Tìm hoặc tạo mới Candidate
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
        candidate.setAvatar(request.getAvatar());
        candidate.setCoverImage(request.getCoverImage());
        candidate.setAddress(request.getAddress());

        return candidateRepo.save(candidate);
    }

    private Account getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return accountRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account không tồn tại"));
    }
}