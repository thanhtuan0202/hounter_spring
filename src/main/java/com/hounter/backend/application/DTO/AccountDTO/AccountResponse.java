package com.hounter.backend.application.DTO.AccountDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.hounter.backend.business_logic.entities.Account;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse{
    public Long id;
    public String username;
    public String full_name;
    public Boolean isActive;
    public LocalDate createAt;

    public AccountResponse(Long id, String username, String full_name, Boolean isActive, LocalDate createAt) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.isActive = isActive;
        this.createAt = createAt;
    }

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.full_name = account.getFull_name();
        this.isActive = account.getIsActive();
        this.createAt = account.getCreateAt();
    }
}
