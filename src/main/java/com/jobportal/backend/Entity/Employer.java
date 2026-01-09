package com.jobportal.backend.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "employers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer {

    @Id
    private Integer id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Account account;


    @Size(max = 200)
    private String employerName;


    @Size(max = 100)
    private String representativeName;


    @Size(max = 50)
    private String representativePosition;


    @Size(max = 20)
    private String phone;


    @Size(max = 200)
    private String coverImage;


    @Size(max = 200)
    private String logoImage;


    @Size(max = 50)
    private String scale;


    @Size(max = 1000)
    private String description;


    @Size(max = 250)
    private String address;


    @Size(max = 100)
    private String website;
}

