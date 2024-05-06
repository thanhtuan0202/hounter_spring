package com.hounter.backend.application.DTO.PostDto;

import com.hounter.backend.shared.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPostDto {
    public Integer wardId;
    public Integer upperPrice;
    public Integer lowerPrice;
    public Long category;
    public Status status;
}
