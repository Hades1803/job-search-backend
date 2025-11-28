package com.jobportal.backend.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "candidates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Account account;


    @Size(max = 200)
    private String name;


    @Size(max = 20)
    private String phone;


    @Size(max = 10)
    private String gender;


    private LocalDate birthDate;

    @Size(max = 200)
    private String avatar;


    @Size(max = 200)
    private String coverImage;


    @Size(max = 250)
    private String address;
}
