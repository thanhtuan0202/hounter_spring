package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AccountDTO.*;
import com.hounter.backend.application.DTO.AuthDTO;

import java.util.Set;

public interface AccountService {
    public AuthDTO login(LoginDTO loginDTO) throws Exception;

    public AccountResponse register(RegisterDTO registerDTO) throws Exception;

    public boolean changePassword(ChangePasswordDTO changePasswordDTO, Long id) throws Exception;
    public boolean changeActive(Long id);
    public Set<AccountResponse> getAllAccounts();

}
