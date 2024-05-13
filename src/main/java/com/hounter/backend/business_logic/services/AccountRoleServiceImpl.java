package com.hounter.backend.business_logic.services;

import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Role;
import com.hounter.backend.business_logic.interfaces.AccountRoleService;
import com.hounter.backend.data_access.repositories.AccountRepository;
import com.hounter.backend.data_access.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountRoleServiceImpl implements AccountRoleService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enrollAccountToRole(Long accountId, String role_name) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        Optional<Role> optionalRole = this.roleRepository.findByName(role_name);

        if(optionalRole.isPresent() && optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            Role role = optionalRole.get();
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            account.setRoles(roles);
            this.accountRepository.save(account);
        }
        else{
            throw new RuntimeException();
        }
    }
}
