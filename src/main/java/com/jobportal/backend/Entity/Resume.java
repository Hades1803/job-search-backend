package com.jobportal.backend.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Size(max = 200)
    private String resumeName;


    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;


    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;


    @ManyToOne
    @JoinColumn(name = "job_type_id")
    private JobType jobType;


    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;


    @Column(columnDefinition = "TEXT")
    private String careerObjective;


    @Column(columnDefinition = "TEXT")
    private String experience;


    @Size(max = 500)
    private String skills;


    @Column(columnDefinition = "TEXT")
    private String education;


    @Size(max = 500)
    private String softSkills;


    @Size(max = 500)
    private String awards;
}
