package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.AccountDTO.*;
import com.hounter.backend.application.DTO.AuthDTO;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Customer;

import com.hounter.backend.business_logic.interfaces.AccountRoleService;
import com.hounter.backend.business_logic.interfaces.AccountService;
import com.hounter.backend.data_access.repositories.AccountRepository;
import com.hounter.backend.data_access.repositories.CustomerRepository;
import com.hounter.backend.shared.exceptions.ConfirmPasswordNotMatch;
import com.hounter.backend.shared.utils.JwtUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRoleService accountRoleService;

    public AccountServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AuthDTO login(LoginDTO loginDTO) throws Exception {
        Optional<Account> account = accountRepository.findByUsername(loginDTO.getUsername());
        if(account.isPresent()) {
            Account currentAccount = account.get();
            boolean matchPassword = this.passwordEncoder.matches(loginDTO.getPassword(),currentAccount.getPassword());
            if(matchPassword) {
                if(!currentAccount.getIsActive()){
                    throw new Exception("Tài khoản hiện đang bị khoá. Vui lòng thử lại hoặc liên hệ hỗ trợ!");
                }
                String token = JwtUtils.generateToken(currentAccount);
                return new AuthDTO(currentAccount.getId(),currentAccount.getUsername(), currentAccount.getEmail(),currentAccount.getFull_name(),currentAccount.getAvatar(),token);
            }
            else{
                throw new Exception("Thông tin tài khoản hoặc mật khẩu không chính xác.");
            }
        }
        else{
            throw new Exception("Thông tin tài khoản hoặc mật khẩu không chính xác.");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public AccountResponse register(RegisterDTO registerDTO) throws Exception{
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            throw new ConfirmPasswordNotMatch("Mật khẩu không khớp, vui lòng nhập lại!");
        }
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());

        Customer customer = new Customer();

        customer.setUsername(registerDTO.getUsername());
        customer.setPassword(hashPassword);
        customer.setFull_name(registerDTO.getFull_name());

//        customer.setId(new_account.getId());
        customer.setBalance(0);
        customer.setIsActive(true);
        customer.setCreateAt(LocalDate.now());
        customer.setUpdateAt(LocalDate.now());
        Customer cus = this.customerRepository.save(customer);
        this.accountRoleService.enrollAccountToRole(cus.getId(), "USER");
        return new AccountResponse(cus.getId(), cus.getUsername(), cus.getFull_name(),cus.getIsActive(), cus.getCreateAt());
    }
    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO, Long id) throws Exception {
        if(!changePasswordDTO.getNew_password().equals(changePasswordDTO.getConfirmPassword())){
            throw new ConfirmPasswordNotMatch("Mật khẩu không khớp, vui lòng nhập lại!");
        }
        Optional<Account> account = this.accountRepository.findById(id);
        if(account.isPresent()){
            Account exist_account = account.get();
            boolean is_match = this.passwordEncoder.matches(changePasswordDTO.getOld_password(),exist_account.getPassword());
            if(is_match){
                exist_account.setPassword(this.passwordEncoder.encode(changePasswordDTO.getNew_password()));
                exist_account.setUpdateAt(LocalDate.now());
                this.accountRepository.save(exist_account);
                return true;
            }
            else{
                throw new Exception("Mật khẩu cũ không chính xác.");
            }
        }
        return false;
    }

    @Override
    public boolean changeActive(Long id) {
        Optional<Account> account = this.accountRepository.findById(id);
        if(account.isPresent()){
            Account exist_account = account.get();
            boolean is_active = exist_account.getIsActive();
            exist_account.setIsActive(!is_active);
            exist_account.setUpdateAt(LocalDate.now());
            this.accountRepository.save(exist_account);
            return true;
        }
        return false;
    }

    @Override
    public Set<AccountResponse> getAllAccounts() {
        List<Account> accounts_lst = this.accountRepository.findAll();
        Set<AccountResponse> response = new HashSet<AccountResponse>();
        for(Account item: accounts_lst){
            response.add(new AccountResponse(item.getId(), item.getUsername(), item.getFull_name(),item.getIsActive(),item.getCreateAt()));
        }
        return response;
    }
}
