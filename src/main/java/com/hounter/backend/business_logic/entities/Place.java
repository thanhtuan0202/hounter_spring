package com.hounter.backend.business_logic.entities;

import org.hibernate.annotations.ManyToAny;

import com.hounter.backend.shared.enums.PlaceType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    PlaceType type;
    @NotBlank
    String name;
    
    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "code")
    District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "code")
    Ward ward;

    @NotBlank
    String address;
    
    @NotNull
    Float latitude;
    
    @NotNull
    Float longitude;

    public String getType() {
        return this.type.toString();
    }
    
    public void setType(String type) {
        this.type = PlaceType.valueOf(type);
    }
}
