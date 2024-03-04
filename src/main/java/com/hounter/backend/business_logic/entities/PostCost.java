package com.hounter.backend.business_logic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "post_cost")
@Getter
@Setter
@NoArgsConstructor
public class PostCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "active_days", nullable = false)
    private Integer activeDays;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id",nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "cost_id", referencedColumnName = "id", nullable = false)
    private Cost cost;
}
