package com.hounter.backend.business_logic.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hounter.backend.application.DTO.CustomUserDetail;
import com.hounter.backend.application.DTO.NotifyDTO;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Notify;
import com.hounter.backend.business_logic.entities.Payment;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.data_access.repositories.AccountRepository;
import com.hounter.backend.data_access.repositories.NotifyRepository;
import com.hounter.backend.shared.enums.NotifyPrepositional;
import com.hounter.backend.shared.enums.NotifyRedirectType;
import com.hounter.backend.shared.enums.NotifyVerb;

import java.util.Comparator;
import java.util.List;


@Service
public class NotifyService {
    @Autowired
    private NotifyRepository notifyRepository; 

    @Autowired
    private AccountRepository accountRepository;

    public NotifyDTO ReadNotify(Long notifyId) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = ((Account) principal).getId();
        Optional <Account> account = accountRepository.findById(accountId);
        if (!account.isPresent()) {
            throw new RuntimeException("Account not found");
        }
        NotifyDTO notifyDTO = new NotifyDTO(notifyRepository.findById(notifyId).get());
        notifyDTO.setIsRead(true);
        notifyRepository.save(notifyDTO.toEntity(account.get()));
        return notifyDTO;
    }

    public List<NotifyDTO> getNotifies() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = ((CustomUserDetail) principal).getUserId();
        Optional <Account> account = accountRepository.findById(accountId);
        if (!account.isPresent()) {
            throw new RuntimeException("Account not found");
        }
        return notifyRepository.findByAccount_Id(accountId).stream()
            .map(NotifyDTO::new)
            .sorted(Comparator.comparing(NotifyDTO::getCreateAt).reversed())
            .collect(Collectors.toList());
    }

    public void createNotifyPaySuccess(Payment payment) {
        Account account = payment.getCustomer();
        Notify notify = new Notify();
        notify.setAccount(account);
        notify.setSubject("You");
        notify.setVerb(NotifyVerb.PAY);
        notify.setDirectObject("payment " + payment.getId());   
        notify.setRedirectType(NotifyRedirectType.PAYMENT);
        notify.setRedirectId(payment.getId());

        notifyRepository.save(notify);
    }

    public Notify createNotifyPayExpired(Payment payment) {
        Account account = payment.getCustomer();
        Notify notify = new Notify();
        notify.setAccount(account);
        notify.setSubject("Payment " + payment.getId());
        notify.setVerb(NotifyVerb.EXPIRED);
        notify.setPrepositionalObject(NotifyPrepositional.FOR);
        notify.setIndirectObject("Post " + payment.getPostNum());
        notify.setRedirectType(NotifyRedirectType.PAYMENT);
        notify.setRedirectId(payment.getId());
        return notify;
    }

    public void createNotifyDeleteAccount(Account account) {
        Optional <Account> adminAccount = accountRepository.findByUsername("admin");
        if (!adminAccount.isPresent()) {
            throw new RuntimeException("Account not found");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetail currentAccount = (CustomUserDetail) principal;
        // Authentication currentAccount = SecurityContextHolder.getContext().getAuthentication();
        // String username = currentAccount.getName();
        Notify notify = new Notify();
        notify.setAccount(adminAccount.get());
        notify.setSubject(currentAccount.getUsername());
        notify.setVerb(NotifyVerb.REQUEST_DELETE);
        notify.setDirectObject("Tài khoản " + account.getUsername());
        notify.setRedirectType(NotifyRedirectType.ACCOUNT);
        notify.setRedirectId(account.getId());
        notifyRepository.save(notify);
    }

    public void createNotifyDeletePost(Post post) {
        Optional <Account> adminAccount = accountRepository.findByUsername("admin");
        if (!adminAccount.isPresent()) {
            throw new RuntimeException("Account not found");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetail currentAccount = (CustomUserDetail) principal;
        Notify notify = new Notify();
        notify.setAccount(adminAccount.get());
        notify.setSubject(currentAccount.getUsername());
        notify.setVerb(NotifyVerb.REQUEST_DELETE);
        notify.setDirectObject("Bài viết " + post.getId());
        notify.setRedirectType(NotifyRedirectType.POST);
        notify.setRedirectId(post.getId());
        notifyRepository.save(notify);
    }
}
