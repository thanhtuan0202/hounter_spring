package com.hounter.backend.business_logic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "staffs")
@Getter
@Setter
@NoArgsConstructor
public class Staff extends Account {

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "start_date")
    private LocalDate startDate;
    
    @OneToMany(mappedBy = "staff")
    private Set<Report> reports;
}
