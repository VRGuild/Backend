package com.mtvs.devlinkbackend.comment.dto.response;

import com.mtvs.devlinkbackend.comment.entity.Comment;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentListResponseDTO {
    private List<Comment> data;
}
