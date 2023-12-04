package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.CustomUserDetail;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.data_access.repositories.AccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailServiceImpl(AccountRepository userRepository) {
        this.accountRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Account accountEntity = account.get();
        return new CustomUserDetail(accountEntity.getUsername(), accountEntity.getPassword(), accountEntity.getRoles(),accountEntity.getId());
    }

    public CustomUserDetail getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            return (CustomUserDetail) authentication.getPrincipal();
        } else {
            return null;
        }
    }
}

