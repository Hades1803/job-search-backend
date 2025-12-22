package com.jobportal.backend;

import com.jobportal.backend.Entity.Role;
import com.jobportal.backend.Entity.RoleType;
import com.jobportal.backend.Repository.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
    @Bean
    public CommandLineRunner initRoles(RoleRepo roleRepo) {
        return args -> {

            if (roleRepo.count() <= 2) {
                roleRepo.save(new Role(101, RoleType.ADMIN));
                roleRepo.save(new Role(102, RoleType.CANDIDATE));
                roleRepo.save(new Role(103, RoleType.EMPLOYER));

                System.out.println("Created default roles!");


            } else {
                System.out.println("Roles already exist → skip");
            }
        };
    }
}
