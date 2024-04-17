package com.hounter.backend.application.DTO.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.hounter.backend.shared.utils.FindPointsAddress;

@Setter
@Getter
@NoArgsConstructor
public class FindPostDTO {
    public Integer area;
    public List <FindPointsAddress.LatLng> points;
}
