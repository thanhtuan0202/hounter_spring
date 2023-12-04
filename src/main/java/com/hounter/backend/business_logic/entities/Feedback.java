package com.hounter.backend.business_logic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 10000, nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id",referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "post_id",referencedColumnName = "id", nullable = false)
    private Post post;

}
