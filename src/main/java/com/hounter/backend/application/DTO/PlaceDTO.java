package com.hounter.backend.application.DTO;

import com.hounter.backend.business_logic.entities.Place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlaceDTO {
    public Long id;
    public String type;
    public String name;
    public String address;
    public Float latitude;
    public Float longitude;

    public PlaceDTO(Place place) {
        this.id = place.getId();
        this.type = place.getType();
        this.name = place.getName();
        this.address = place.getAddress();
        this.latitude = place.getLatitude();
        this.longitude = place.getLongitude();
    }
}
