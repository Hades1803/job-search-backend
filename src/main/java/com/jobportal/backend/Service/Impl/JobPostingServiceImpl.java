package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Config.AppConstraints;
import com.jobportal.backend.Dto.JobPostingRequest;
import com.jobportal.backend.Dto.JobPostingResponse;
import com.jobportal.backend.Entity.*;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.*;
import com.jobportal.backend.Service.JobPostingService;
import com.jobportal.backend.Utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepo jobPostingRepo;
    private final EmployerRepo employerRepo;
    private final MajorRepo majorRepo;
    private final JobTypeRepo jobTypeRepo;
    private final SalaryLevelRepo salaryLevelRepo;
    private final RankRepo rankRepo;
    private final AddressRepo addressRepo;
    private final AccountRepo accountRepo;
    private final SecurityUtils securityUtils;

    // ========== EMPLOYER METHODS ==========

    @Override
    public JobPosting createJobPosting(JobPostingRequest request) {
        if (!securityUtils.isEmployer()) {
            throw new AccessDeniedException("Chỉ nhà tuyển dụng mới được đăng bài tuyển dụng");
        }

        Employer employer = getCurrentEmployer();

        if (employer.getAccount() != null && !employer.getAccount().getStatus()) {
            throw new IllegalStateException("Tài khoản của bạn đã bị khóa");
        }

        if (request.getExpirationDate() != null &&
                request.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau thời điểm hiện tại");
        }

        JobPosting job = new JobPosting();

        // Set basic info
        job.setJobTitle(request.getJobTitle());
        job.setWorkAddress(request.getWorkAddress());
        job.setQuantity(request.getQuantity());
        job.setGenderRequirement(request.getGenderRequirement());
        job.setJobDescription(request.getJobDescription());
        job.setCandidateRequirement(request.getCandidateRequirement());
        job.setRelatedSkills(request.getRelatedSkills());
        job.setBenefits(request.getBenefits());
        job.setExpirationDate(request.getExpirationDate());
        job.setNote(request.getNote());

        // Set employer
        job.setEmployer(employer);

        // Set reference entities
        if (request.getMajorId() != null) {
            Major major = majorRepo.findById(request.getMajorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ngành nghề: " + request.getMajorId()));
            job.setMajor(major);
        }

        if (request.getJobTypeId() != null) {
            JobType jobType = jobTypeRepo.findById(request.getJobTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại công việc: " + request.getJobTypeId()));
            job.setJobType(jobType);
        }

        if (request.getSalaryLevelId() != null) {
            SalaryLevel salaryLevel = salaryLevelRepo.findById(request.getSalaryLevelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy mức lương: " + request.getSalaryLevelId()));
            job.setSalaryLevel(salaryLevel);
        }

        if (request.getRankId() != null) {
            Rank rank = rankRepo.findById(request.getRankId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cấp bậc: " + request.getRankId()));
            job.setRank(rank);
        }

        if (request.getAddressId() != null) {
            Address address = addressRepo.findById(request.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy địa chỉ: " + request.getAddressId()));
            job.setAddress(address);
        }

        job.setPostedDate(LocalDateTime.now());
        job.setStatus(true);
        job.setViews(0);

        return jobPostingRepo.save(job);
    }

    @Override
    public JobPosting updateJobPosting(Integer id, JobPostingRequest request) {
        JobPosting job = jobPostingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài đăng tuyển dụng ID: " + id));

        if (securityUtils.isEmployer()) {
            Employer employer = getCurrentEmployer();
            if (!job.getEmployer().getId().equals(employer.getId())) {
                throw new AccessDeniedException("Bạn chỉ có thể chỉnh sửa bài đăng của mình");
            }
        } else if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa bài đăng này");
        }

        if (request.getExpirationDate() != null &&
                request.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau thời điểm hiện tại");
        }

        // Update basic fields
        if (request.getJobTitle() != null) job.setJobTitle(request.getJobTitle());
        if (request.getWorkAddress() != null) job.setWorkAddress(request.getWorkAddress());
        if (request.getQuantity() != null) job.setQuantity(request.getQuantity());
        if (request.getGenderRequirement() != null) job.setGenderRequirement(request.getGenderRequirement());
        if (request.getJobDescription() != null) job.setJobDescription(request.getJobDescription());
        if (request.getCandidateRequirement() != null) job.setCandidateRequirement(request.getCandidateRequirement());
        if (request.getRelatedSkills() != null) job.setRelatedSkills(request.getRelatedSkills());
        if (request.getBenefits() != null) job.setBenefits(request.getBenefits());
        if (request.getExpirationDate() != null) job.setExpirationDate(request.getExpirationDate());
        if (request.getNote() != null) job.setNote(request.getNote());
//        if (request.getStatus() != null) job.setStatus(request.getStatus());

        // Update reference entities
        if (request.getMajorId() != null) {
            Major major = majorRepo.findById(request.getMajorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ngành nghề: " + request.getMajorId()));
            job.setMajor(major);
        }

        if (request.getJobTypeId() != null) {
            JobType jobType = jobTypeRepo.findById(request.getJobTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại công việc: " + request.getJobTypeId()));
            job.setJobType(jobType);
        }

        if (request.getSalaryLevelId() != null) {
            SalaryLevel salaryLevel = salaryLevelRepo.findById(request.getSalaryLevelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy mức lương: " + request.getSalaryLevelId()));
            job.setSalaryLevel(salaryLevel);
        }

        if (request.getRankId() != null) {
            Rank rank = rankRepo.findById(request.getRankId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy cấp bậc: " + request.getRankId()));
            job.setRank(rank);
        }

        if (request.getAddressId() != null) {
            Address address = addressRepo.findById(request.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy địa chỉ: " + request.getAddressId()));
            job.setAddress(address);
        }

        return jobPostingRepo.save(job);
    }

    @Override
    public void deleteJobPosting(Integer id) {
        JobPosting job = jobPostingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài đăng tuyển dụng ID: " + id));

        if (securityUtils.isAdmin()) {
            jobPostingRepo.delete(job);
            return;
        }

        if (securityUtils.isEmployer()) {
            Employer employer = getCurrentEmployer();
            if (!job.getEmployer().getId().equals(employer.getId())) {
                throw new AccessDeniedException("Bạn chỉ có thể xóa bài đăng của mình");
            }
            jobPostingRepo.delete(job);
            return;
        }

        throw new AccessDeniedException("Bạn không có quyền xóa bài đăng này");
    }

    @Override
    public List<JobPosting> getMyJobPostings() {
        if (!securityUtils.isEmployer()) {
            throw new AccessDeniedException("Chỉ nhà tuyển dụng mới có thể xem danh sách bài đăng của mình");
        }

        Employer employer = getCurrentEmployer();
        return jobPostingRepo.findByEmployerId(employer.getId());
    }

    @Override
    public JobPosting getMyJobPostingById(Integer id) {
        if (!securityUtils.isEmployer()) {
            throw new AccessDeniedException("Chỉ nhà tuyển dụng mới có thể xem chi tiết bài đăng của mình");
        }

        Employer employer = getCurrentEmployer();
        JobPosting job = jobPostingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài đăng tuyển dụng ID: " + id));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new AccessDeniedException("Bạn chỉ có thể xem bài đăng của mình");
        }

        return job;
    }

    // ========== PUBLIC METHODS ==========

    @Override
    public Page<JobPostingResponse> getAllActiveJobPostings(Pageable pageable) {
        Page<JobPosting> jobPostings = jobPostingRepo.findByStatusTrueAndExpirationDateAfter(
                LocalDateTime.now(), pageable);
        return jobPostings.map(this::convertToResponse);
    }

    @Override
    public JobPostingResponse getJobPostingById(Integer id) {
        JobPosting job = jobPostingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài đăng tuyển dụng ID: " + id));

        // Tăng lượt xem
        job.setViews(job.getViews() + 1);
        jobPostingRepo.save(job);

        return convertToResponse(job);
    }

    @Override
    public Page<JobPostingResponse> searchJobPostings(String keyword,
                                                      String majorId,
                                                      String jobTypeId,
                                                      String addressId,
                                                      Pageable pageable) {
        log.debug("Searching job postings - keyword: {}, majorId: {}, jobTypeId: {}, addressId: {}",
                keyword, majorId, jobTypeId, addressId);

        Specification<JobPosting> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Chỉ hiển thị job active và chưa hết hạn
            predicates.add(cb.equal(root.get("status"), true));
            predicates.add(cb.greaterThan(root.get("expirationDate"), LocalDateTime.now()));

            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchKeyword = "%" + keyword.toLowerCase() + "%";
                Predicate titlePredicate = cb.like(cb.lower(root.get("jobTitle")), searchKeyword);
                Predicate descPredicate = cb.like(cb.lower(root.get("jobDescription")), searchKeyword);
                Predicate companyPredicate = cb.like(cb.lower(root.get("employer").get("companyName")), searchKeyword);
                predicates.add(cb.or(titlePredicate, descPredicate, companyPredicate));
            }

            if (majorId != null && !majorId.trim().isEmpty()) {
                Join<JobPosting, Major> majorJoin = root.join("major");
                predicates.add(cb.equal(majorJoin.get("id"), majorId));
            }

            if (jobTypeId != null && !jobTypeId.trim().isEmpty()) {
                Join<JobPosting, JobType> jobTypeJoin = root.join("jobType");
                predicates.add(cb.equal(jobTypeJoin.get("id"), jobTypeId));
            }

            if (addressId != null && !addressId.trim().isEmpty()) {
                Join<JobPosting, Address> addressJoin = root.join("address");
                predicates.add(cb.equal(addressJoin.get("id"), addressId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<JobPosting> jobPostings = jobPostingRepo.findAll(spec, pageable);
        log.debug("Found {} job postings", jobPostings.getTotalElements());

        return jobPostings.map(this::convertToResponse);
    }

    // ========== ADMIN METHODS ==========

    @Override
    public Page<JobPosting> getAllJobPostings(Pageable pageable) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("Chỉ quản trị viên mới có thể xem tất cả bài đăng");
        }

        return jobPostingRepo.findAll(pageable);
    }

    @Override
    public JobPosting toggleJobPostingStatus(Integer id) {
        if (!securityUtils.isAdmin()) {
            throw new AccessDeniedException("Chỉ quản trị viên mới có thể thay đổi trạng thái bài đăng");
        }

        JobPosting job = jobPostingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài đăng tuyển dụng ID: " + id));

        job.setStatus(!job.getStatus());
        return jobPostingRepo.save(job);
    }

    // ========== HELPER METHODS ==========

    private JobPostingResponse convertToResponse(JobPosting job) {
        JobPostingResponse response = new JobPostingResponse();
        response.setId(job.getId());
        response.setJobTitle(job.getJobTitle());
        response.setWorkAddress(job.getWorkAddress());
        response.setQuantity(job.getQuantity());
        response.setGenderRequirement(job.getGenderRequirement());
        response.setJobDescription(job.getJobDescription());
        response.setCandidateRequirement(job.getCandidateRequirement());
        response.setRelatedSkills(job.getRelatedSkills());
        response.setBenefits(job.getBenefits());
        response.setPostedDate(job.getPostedDate());
        response.setExpirationDate(job.getExpirationDate());
        response.setViews(job.getViews());
        response.setNote(job.getNote());
        response.setStatus(job.getStatus());

        // Map employer
        if (job.getEmployer() != null) {
            response.setEmployerName(job.getEmployer().getEmployerName());
            response.setEmployerLogo(job.getEmployer().getLogoImage());
        }

        // Map references với ID String
        if (job.getMajor() != null) {
            response.setMajorId(job.getMajor().getId());
            response.setMajorName(job.getMajor().getName());
        }

        if (job.getJobType() != null) {
            response.setJobTypeId(job.getJobType().getId());
            response.setJobTypeName(job.getJobType().getName());
        }

        if (job.getSalaryLevel() != null) {
            response.setSalaryLevelId(job.getSalaryLevel().getId());
            response.setSalaryLevelName(job.getSalaryLevel().getName());
        }

        if (job.getRank() != null) {
            response.setRankId(job.getRank().getId());
            response.setRankName(job.getRank().getName());
        }

        if (job.getAddress() != null) {
            response.setAddressId(job.getAddress().getId());
            response.setAddressName(job.getAddress().getName());
        }

        return response;
    }

    private Employer getCurrentEmployer() {
        String email = securityUtils.getCurrentUserEmail();

        Account account = accountRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        String roleName = String.valueOf(account.getRole().getName());

        // Cho phép: EMPLOYER / ROLE_EMPLOYER / Employer
        if (roleName == null ||
                !roleName.equalsIgnoreCase(AppConstraints.ROLE_EMPLOYER) &&
                        !roleName.equalsIgnoreCase("ROLE_" + AppConstraints.ROLE_EMPLOYER)) {

            throw new IllegalStateException("Tài khoản không phải nhà tuyển dụng");
        }

        return employerRepo.findByAccountId(account.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hồ sơ nhà tuyển dụng"));
    }
}