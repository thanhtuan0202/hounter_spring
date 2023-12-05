package com.hounter.backend.business_logic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends Account{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "balance")
    private Integer balance;

    @OneToMany(mappedBy = "customer")
    private Set<Post> posts;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<FavoritePost> favoritePosts;

    @OneToMany(mappedBy = "customer")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "customer")
    private Set<History> histories;

    @OneToMany(mappedBy = "customer")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "customer")
    private Set<Report> reports;
}
