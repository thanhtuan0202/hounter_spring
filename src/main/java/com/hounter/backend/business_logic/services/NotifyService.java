package com.hounter.backend.business_logic.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        NotifyDTO notifyDTO = new NotifyDTO(notifyRepository.findById(notifyId).get());
        notifyDTO.setIsRead(true);
        notifyRepository.save(notifyDTO.toEntity(account.get()));
        return notifyDTO;
    }

    public List<NotifyDTO> getNotifies(Integer pageNo, Integer pageSize) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long accountId = ((CustomUserDetail) principal).getUserId();
        Optional <Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString("desc"), "createdAt"));
        return notifyRepository.findByAccount(account.get(), pageable).stream()
            .map(NotifyDTO::new)
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
        notify.setIndirectObject("Post " + payment.getPostCost().getPost().getId());
        notify.setRedirectType(NotifyRedirectType.PAYMENT);
        notify.setRedirectId(payment.getId());
        return notify;
    }

    public void createNotifyDeleteAccount(Account account) {
        Optional <Account> adminAccount = accountRepository.findByUsername("admin");
        if (adminAccount.isEmpty()) {
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

    public void createNotifyDeletePostByAdmin(Post post) {
        Account account = post.getCustomer();
        Notify notify = new Notify();
        notify.setAccount(account);
        notify.setSubject("Admin");
        notify.setVerb(NotifyVerb.DELETE);
        notify.setDirectObject("Bài viết " + post.getId());
        notify.setRedirectType(NotifyRedirectType.POST);
        notify.setRedirectId(post.getId());
        notifyRepository.save(notify);
    }
}
