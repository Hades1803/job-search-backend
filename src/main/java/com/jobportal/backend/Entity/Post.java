package com.jobportal.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Table(name = "posts")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Size(max = 200)
    private String postName;


    @Size(max = 100)
    private String title;


    @Size(max = 200)
    private String mainImage;


    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    private LocalDateTime createdAt;


    private Integer views = 0;


    @Size(max = 500)
    private String note;


    private Boolean status = true;
}
