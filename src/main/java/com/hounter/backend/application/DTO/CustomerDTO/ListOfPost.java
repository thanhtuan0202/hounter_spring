package com.hounter.backend.application.DTO.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListOfPost {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDate createAt;
    private LocalDate expireAt;
    private PostCostRes cost;
}
