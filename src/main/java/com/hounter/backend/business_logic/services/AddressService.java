package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.AddressDTO;
import com.hounter.backend.business_logic.entities.District;
import com.hounter.backend.business_logic.entities.Province;
import com.hounter.backend.business_logic.entities.Ward;
import com.hounter.backend.data_access.repositories.DistrictRepository;
import com.hounter.backend.data_access.repositories.ProvinceRepository;
import com.hounter.backend.data_access.repositories.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

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

    public AddressDTO.WardDTO findWardId(String ward, String district, String province){
        Ward ward_ = this.wardRepository.findWard(ward, district, province);
        return new AddressDTO.WardDTO(ward_);
    }
}
