package com.hounter.backend.business_logic.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @ManyToOne
    @JoinColumn(name = "ward_id",referencedColumnName = "code", nullable = false)
    private Ward ward;

    @ManyToOne
    @JoinColumn(name = "district_id",referencedColumnName = "code", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "code", nullable = false)
    private Province province;

    @Override
    public String toString(){
        return street + ", " + ward.getNameWithType() + ", " + district.getNameWithType() + ", "  + province.getNameWithType();
    }

    public Address(String street, Ward ward){
        this.street = street;
        this.ward = ward;
        this.district = ward.getDistrict();
        this.province = ward.getDistrict().getProvince();
    }
}
