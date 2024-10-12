package com.mtvs.devlinkbackend.comment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentUpdateRequestDTO {
    private Long commentId;
    private String content;
}
