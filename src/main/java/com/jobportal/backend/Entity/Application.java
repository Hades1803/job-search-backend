package com.jobportal.backend.Entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume; // Có thể null nếu dùng file upload

    private String resumeLink;

    // Thêm các field mới
    private String uploadedCVPath; // Đường dẫn file upload
    private String uploadedCVName; // Tên file upload

    @Column(name = "resume_type")
    @Enumerated(EnumType.STRING)
    private ResumeType resumeType; // Loại resume sử dụng

    private LocalDateTime applyDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public enum ResumeType {
        DB_RESUME,
        UPLOADED_FILE,
        LINK_ONLY
    }
}