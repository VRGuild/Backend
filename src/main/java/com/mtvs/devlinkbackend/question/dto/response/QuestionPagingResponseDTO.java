package com.mtvs.devlinkbackend.question.dto.response;

import com.mtvs.devlinkbackend.question.entity.Question;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionPagingResponseDTO {
    private List<Question> data;
    private Integer totalPages;
}
