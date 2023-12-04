package com.hounter.backend.business_logic.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String description;

    @Column(nullable = false, name = "create_date")
    private LocalDate createDate;

    @Column(nullable = false, name = "close_date")
    private LocalDate closeDate;

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff", referencedColumnName = "id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "history", referencedColumnName = "id", nullable = false)
    private History history;
}
