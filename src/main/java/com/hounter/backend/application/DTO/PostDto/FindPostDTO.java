package com.hounter.backend.application.DTO.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class FindPostDTO {
    @NotBlank
    public String address;
    public Integer area;
    public List <String> extension;
}
