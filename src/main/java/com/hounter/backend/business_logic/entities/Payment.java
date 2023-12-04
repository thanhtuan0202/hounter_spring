package com.hounter.backend.business_logic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.hounter.backend.shared.enums.PaymentStatus;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.waiting;

    @Column(name = "total_price",nullable = false)
    private Integer totalPrice;

    @Column(name = "create_at",nullable = false)
    private LocalDate createAt;

    @Column(name = "expire_at",nullable = false)
    private LocalDate expireAt;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_id")
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "post_cost_id", referencedColumnName = "id",nullable = false)
    private PostCost postCost;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id",nullable = false)
    private Customer customer;
}
