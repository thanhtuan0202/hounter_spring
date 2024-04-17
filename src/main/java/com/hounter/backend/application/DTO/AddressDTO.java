package com.hounter.backend.application.DTO;

import java.util.ArrayList;
import java.util.List;

import com.hounter.backend.business_logic.entities.Address;
import com.hounter.backend.business_logic.entities.District;
import com.hounter.backend.business_logic.entities.Province;
import com.hounter.backend.business_logic.entities.Ward;

public class AddressDTO {
    
    public static class AddressResDTO{
        public String province;
        public String district;
        public String ward;
        public String street;

        public AddressResDTO(Address address){
            this.province = address.getProvince().getName();
            this.district = address.getDistrict().getName();
            this.ward = address.getWard().getName();
            this.street = address.getStreet();
        }
    }

    public static class ProvinceDTO{
        public Integer code;
        public String nameWithType;

        public ProvinceDTO(Province province){
            this.code = province.getCode();
            this.nameWithType = province.getNameWithType();
        }
    }

    public static class DistrictDTO{
        public Integer code;
        public String nameWithType;
        public List<WardDTO> wards;

        public DistrictDTO(District district){
            this.code = district.getCode();
            this.nameWithType = district.getNameWithType();
            this.wards = new ArrayList<>();
            for(Ward ward: district.getWards()){
                this.wards.add(new WardDTO(ward));
            }
        }
    }

    public static class WardDTO{
        public Integer code;
        public String nameWithType;

        public WardDTO(Ward ward){
            this.code = ward.getCode();
            this.nameWithType = ward.getNameWithType();
        }
    }
}
