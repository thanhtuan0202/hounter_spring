package com.hounter.backend.business_logic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hounter.backend.application.DTO.AddressDTO;
import com.hounter.backend.data_access.repositories.DistrictRepository;
import com.hounter.backend.data_access.repositories.ProvinceRepository;
import com.hounter.backend.business_logic.entities.District;
import com.hounter.backend.business_logic.entities.Province;

@Service
public class AddressService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public List<AddressDTO.ProvinceDTO> getProvinceList(){
        List<Province> provinces = this.provinceRepository.findAll();
        List<AddressDTO.ProvinceDTO> response = new ArrayList<>();
        for(Province province: provinces){
            AddressDTO.ProvinceDTO dto = new AddressDTO.ProvinceDTO(province);
            response.add(dto);
        }
        return response;
    }

    public List<AddressDTO.DistrictDTO> getDetailProvince(Integer provinceId){
        Optional<Province> province = this.provinceRepository.findByCode(provinceId);
        if(province.isEmpty()){
            return null;
        }
        List<District> districts = this.districtRepository.findByProvince(province.get());
        List<AddressDTO.DistrictDTO> response = new ArrayList<>();
        for(District district: districts){
            response.add(new AddressDTO.DistrictDTO(district));
        }
        return response;
    }
}
