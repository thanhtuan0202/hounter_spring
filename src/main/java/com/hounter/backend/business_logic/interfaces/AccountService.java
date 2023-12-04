package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AccountDTO.*;

import java.util.Set;

public interface AccountService {
    public String login(LoginDTO loginDTO) throws Exception;

    public AccountResponse register(RegisterDTO registerDTO) throws Exception;

    public boolean changePassword(ChangePasswordDTO changePasswordDTO, Long id) throws Exception;
    public boolean changeActive(Long id);
    public Set<AccountResponse> getAllAccounts();

}
