package com.jobportal.backend.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Email
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String email;


    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String password;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    private Boolean status;


    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Candidate candidate;


    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Employer employer;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
