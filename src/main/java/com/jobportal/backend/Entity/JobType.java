package com.jobportal.backend.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "job_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobType {

    @Id
    @Size(max = 50)
    private String id;


    @Size(max = 100)
    private String name;
}
