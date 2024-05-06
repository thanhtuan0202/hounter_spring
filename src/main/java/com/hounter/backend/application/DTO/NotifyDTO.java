package com.hounter.backend.application.DTO;

import java.time.LocalDateTime;

import com.hounter.backend.application.DTO.AccountDTO.AccountResponse;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Notify;
import com.hounter.backend.shared.enums.NotifyPrepositional;
import com.hounter.backend.shared.enums.NotifyRedirectType;
import com.hounter.backend.shared.enums.NotifyVerb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class NotifyDTO {
    private Long id;

    public AccountResponse account;
    public String subject;
    public NotifyVerb verb;
    public String directObject;
    public String indirectObject;
    public NotifyPrepositional prepositionalObject;
    public String context;
    public Boolean isRead;
    public NotifyRedirectType redirectType;
    public LocalDateTime createAt;
    public Long redirectId;

    public NotifyDTO(Long id, AccountResponse account, String subject, NotifyVerb verb, String directObject,
            String indirectObject, NotifyPrepositional prepositionalObject, String context, Boolean isRead,
            NotifyRedirectType redirectType, Long redirectId) {
        this.id = id;
        this.account = account;
        this.subject = subject;
        this.verb = verb;
        this.directObject = directObject;
        this.indirectObject = indirectObject;
        this.prepositionalObject = prepositionalObject;
        this.context = context;
        this.isRead = isRead;
        this.redirectType = redirectType;
        this.redirectId = redirectId;
        this.createAt = LocalDateTime.now();
    }

    public NotifyDTO(Notify notify) {
        this.id = notify.getId();
        this.account = new AccountResponse(notify.getAccount());
        this.subject = notify.getSubject();
        this.verb = notify.getVerb();
        this.directObject = notify.getDirectObject();
        this.indirectObject = notify.getIndirectObject();
        this.prepositionalObject = notify.getPrepositionalObject();
        this.context = notify.getContext();
        this.isRead = notify.getIsRead();
        this.redirectType = notify.getRedirectType();
        this.redirectId = notify.getRedirectId();
        this.createAt = notify.getCreatedAt();
    }

    public Notify toEntity(Account account) {
        return new Notify(account, subject, verb, directObject, indirectObject, prepositionalObject, context,
                isRead, redirectType, redirectId);
    }

}
