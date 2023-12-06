package com.hounter.backend.application.DTO.FeedbackDto;

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
    private Long sender;
    private LocalDate create_at;
}
