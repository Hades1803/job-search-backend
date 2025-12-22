package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Entity.Account;
import com.jobportal.backend.Repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account acc = accountRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // SỬA: Không dùng .roles() vì nó tự thêm "ROLE_" prefix
        return User.builder()
                .username(acc.getEmail())
                .password(acc.getPassword())
                // DÙNG .authorities() với SimpleGrantedAuthority
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority(acc.getRole().getName().name())
                ))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!acc.getStatus())
                .build();
    }
}