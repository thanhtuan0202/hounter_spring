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
@Table(name = "costs")
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "description",nullable = false,length = 10000)
    private String description;

    @Column(name = "discont_7days",nullable = false)
    private Integer discount_7days;

    @Column(name = "discont_30days",nullable = false)
    private Integer discount_30days;

    @OneToMany(mappedBy = "cost")
    private Set<PostCost> postCosts;
}
