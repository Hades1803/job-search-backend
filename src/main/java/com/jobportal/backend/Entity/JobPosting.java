package com.jobportal.backend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "job_postings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    @JsonIgnore
    private Employer employer;


    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;


    @ManyToOne
    @JoinColumn(name = "job_type_id")
    private JobType jobType;


    @ManyToOne
    @JoinColumn(name = "salary_level_id")
    private SalaryLevel salaryLevel;


    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;


    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @Size(max = 250)
    private String jobTitle;


    @Size(max = 250)
    private String workAddress;
    private Integer quantity;
    @Size(max = 50)
    private String genderRequirement;


    @Column(columnDefinition = "TEXT")
    private String jobDescription;


    @Column(columnDefinition = "TEXT")
    private String candidateRequirement;


    @Column(columnDefinition = "TEXT")
    private String relatedSkills;


    @Column(columnDefinition = "TEXT")
    private String benefits;


    private LocalDateTime postedDate;


    private LocalDateTime expirationDate;


    private Integer views = 0;


    @Size(max = 500)
    private String note;
    private Boolean status = true;
}
