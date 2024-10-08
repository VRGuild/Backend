package com.mtvs.devlinkbackend.question.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionUpdateRequestDTO {
    private long questionId;
    private String title;
    private String content;
}
