package com.hounter.backend.application.DTO.AccountDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse{
    private Long id;
    private String username;
    private String full_name;
    private Boolean isActive;
    private LocalDate createAt;

    public AccountResponse(Long id, String username, String full_name, Boolean isActive, LocalDate createAt) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.isActive = isActive;
        this.createAt = createAt;
    }
}
