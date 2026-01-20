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
    private Resume resume;

    private String resumeLink;


    private String uploadedCVPath;
    private String uploadedCVName;

    @Column(name = "resume_type")
    @Enumerated(EnumType.STRING)
    private ResumeType resumeType;

    private LocalDateTime applyDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public enum ResumeType {
        DB_RESUME,
        UPLOADED_FILE,
        LINK_ONLY
    }
}