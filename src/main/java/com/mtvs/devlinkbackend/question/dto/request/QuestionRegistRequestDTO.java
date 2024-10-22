package com.mtvs.devlinkbackend.question.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionRegistRequestDTO {
    private String title;
    private String content;
}
