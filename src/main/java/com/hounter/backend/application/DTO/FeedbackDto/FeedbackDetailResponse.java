package com.hounter.backend.application.DTO.FeedbackDto;

import java.time.LocalDate;

import com.hounter.backend.application.DTO.PostDto.ShortCustomer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDetailResponse {
    private Long id;
    private String content;
    private Long postId;
    private String postTitle;
    private String postDescription;
    private ShortCustomer sender;
    private ShortCustomer postOwner;
    private LocalDate createAt;
}
