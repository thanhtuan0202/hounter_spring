package com.hounter.backend.application.DTO.CustomerDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.hounter.backend.application.DTO.AddressDTO;

@Getter
@Setter
@NoArgsConstructor
public class PostOfUserRes {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private Integer area;
    private AddressDTO.AddressResDTO address;
    private String ownerName;
    private String ownerPhone;
    private String category;
    private PostCostRes cost;
    private String notes;
    private List<String> imageList;
}
