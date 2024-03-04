package com.hounter.backend.business_logic.interfaces;

import com.hounter.backend.application.DTO.AccountDTO.*;
import com.hounter.backend.application.DTO.AuthDTO;

import java.util.Set;

public interface AccountService {
    AuthDTO login(LoginDTO loginDTO) throws Exception;
    AccountResponse register(RegisterDTO registerDTO) throws Exception;
    boolean changePassword(ChangePasswordDTO changePasswordDTO, Long id) throws Exception;
    boolean changeActive(Long id);
    Set<AccountResponse> getAllAccounts();

}
