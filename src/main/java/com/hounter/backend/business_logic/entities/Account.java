package com.hounter.backend.business_logic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Account(String username, String password, String full_name){
        this.username = username;
        this.full_name = full_name;
        this.password = password;
        this.isActive = true;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }
}
