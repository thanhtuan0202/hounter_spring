package com.hounter.backend.business_logic.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notify {
    private String title;
    private String directObject;
    private String content;
    private String createAt;
    private Boolean isRead;
    private Integer userId;
}
