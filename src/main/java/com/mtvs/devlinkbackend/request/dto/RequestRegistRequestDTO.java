package com.mtvs.devlinkbackend.request.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestRegistRequestDTO {
    private String title;
    private String content;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}