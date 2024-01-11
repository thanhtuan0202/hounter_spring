package com.hounter.backend.application.DTO.PostDto;

import com.hounter.backend.shared.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
    private Long category;
    private Long customer;
    private Status status;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<String> imageList;
}
