package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account acc = accountRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(acc.getEmail())
                .password(acc.getPassword())
                .roles(acc.getRole().getName().name())
                .build();
    }
}
