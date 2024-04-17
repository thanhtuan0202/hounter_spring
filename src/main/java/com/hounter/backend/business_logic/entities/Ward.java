package com.hounter.backend.business_logic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ward {
    @Id
    private Integer code;

    private String name;

    @Column(name = "code_name")
    private String codeName;

    private String type;

    @Column(name = "name_with_type")
    private String nameWithType;

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "code", nullable = false)
    private District district;
}
