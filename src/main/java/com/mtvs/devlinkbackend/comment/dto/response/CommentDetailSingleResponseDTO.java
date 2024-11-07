package com.mtvs.devlinkbackend.comment.dto.response;

import com.mtvs.devlinkbackend.comment.dto.response.sub.CommentDetailDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDetailSingleResponseDTO {
    private CommentDetailDTO data;
}
