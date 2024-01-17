package com.hounter.backend.application.DTO.FeedbackDto;

import com.hounter.backend.application.DTO.PostDto.ShortCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Long id;
    private String content;
    private Long post;
    private ShortCustomer sender;
    private LocalDate create_at;
}
