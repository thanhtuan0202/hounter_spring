package com.hounter.backend.business_logic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "balance_history")
public class BalanceHistory extends History {

    @Column(name = "new_balance")
    private Integer newBalance;

    @Column(name = "change_value")
    private Integer changeValue;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "type")
    private String type;


}
