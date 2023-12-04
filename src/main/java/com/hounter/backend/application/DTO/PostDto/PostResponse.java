package com.hounter.backend.application.DTO.PostDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
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
    private LocalDate createAt;
    private LocalDate expireAt;

    // @JsonProperty("category_id")
    // private Long category;

    // @JsonProperty("customer_id")
    // private Long customer;

    private List<String> imageList;
}
