package com.hounter.backend.business_logic.interfaces;

public interface AccountRoleService {

    void enrollAccountToRole(Long accountId, String roleId);
}
