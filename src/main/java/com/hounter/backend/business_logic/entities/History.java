package com.hounter.backend.business_logic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "history")
@Inheritance(strategy = InheritanceType.JOINED)
public class History {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "history")
    private Set<Report> reports;

}
