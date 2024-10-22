package com.mtvs.devlinkbackend.comment.dto.response;

import com.mtvs.devlinkbackend.comment.entity.Comment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentSingleResponseDTO {
    private Comment data;
}
