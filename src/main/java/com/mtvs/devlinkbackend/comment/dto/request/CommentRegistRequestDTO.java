package com.mtvs.devlinkbackend.comment.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentRegistRequestDTO {
    private String content;
    private Long requestId;
}
