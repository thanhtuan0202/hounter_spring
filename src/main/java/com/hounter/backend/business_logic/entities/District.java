package com.hounter.backend.business_logic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class District {
    @Id
    private Integer code;

    private String name;

    @Column(name = "code_name")
    private String codeName;

    private String type;

    @Column(name = "name_with_type")
    private String nameWithType;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "code", nullable = false)
    private Province province;
    
    @OneToMany(mappedBy = "district")
    private List<Ward> wards;
}


