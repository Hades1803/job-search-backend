package com.jobportal.backend.Config;

import com.jobportal.backend.Security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.jobportal.backend.Config.AppConstraints.*;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Bật @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // Job posting public endpoints
                        .requestMatchers("/api/job-postings/public/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/error").permitAll()

                        // Role-based endpoints
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole(ROLE_ADMIN)
                        .requestMatchers(EMPLOYER_ENDPOINTS).hasRole(ROLE_EMPLOYER)
                        .requestMatchers(JOB_SEEKER_ENDPOINTS).hasRole(ROLE_JOB_SEEKER)

                        // Job posting employer endpoints
                        .requestMatchers("/api/job-postings/employer/**").hasRole(ROLE_EMPLOYER)
                        .requestMatchers("/api/job-postings/admin/**").hasRole(ROLE_ADMIN)

                        // Candidate endpoints (tương đương job seeker)
                        .requestMatchers("/api/candidate/**").hasRole(ROLE_JOB_SEEKER)

                        // Common authenticated endpoints
                        .requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated()

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}