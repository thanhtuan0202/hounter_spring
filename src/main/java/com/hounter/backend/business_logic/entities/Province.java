package com.hounter.backend.business_logic.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Province {
    @Id
    private Integer code;

    private String name;

    @Column(name = "code_name")
    private String codeName;

    private String type;

    @Column(name = "name_with_type")
    private String nameWithType;

    @OneToMany(mappedBy = "province")
    private List<District> districts;
}
