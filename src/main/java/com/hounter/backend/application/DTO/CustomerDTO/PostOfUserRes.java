package com.hounter.backend.application.DTO.CustomerDTO;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostOfUserRes {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private Integer area;
    private String fullAddress;
    private String city;
    private String county;
    private String district;
    private String ownerName;
    private String ownerPhone;
    private Long category;
    private PostCostRes cost;
    private List<String> imageList;
}
