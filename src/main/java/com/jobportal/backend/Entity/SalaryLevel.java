package com.jobportal.backend.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "salary_levels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryLevel {

    @Id
    @Size(max = 50)
    private String id;


    @Size(max = 50)
    private String name;
}
