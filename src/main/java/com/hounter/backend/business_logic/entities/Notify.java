package com.hounter.backend.business_logic.entities;

import com.hounter.backend.shared.enums.NotifyPrepositional;
import com.hounter.backend.shared.enums.NotifyRedirectType;
import com.hounter.backend.shared.enums.NotifyVerb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    private String subject;
    private NotifyVerb verb;
    private String directObject;
    private String indirectObject;
    private NotifyPrepositional prepositionalObject;
    private String context;
    private Boolean isRead;
    private NotifyRedirectType redirectType;
    private Long redirectId;

    private LocalDateTime createdAt;

    public Notify(Account account, String subject, NotifyVerb verb, String directObject, String indirectObject,
            NotifyPrepositional prepositionalObject, String context, Boolean isRead, NotifyRedirectType redirectType,
            Long redirectId) {
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
        this.createdAt = LocalDateTime.now();
    }
}
