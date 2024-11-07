package com.mtvs.devlinkbackend.comment.dto.response.sub;

import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.UserDetailResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDetailDTO {
    private Long commentId;
    private UserDetailResponseDTO userInfo;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
