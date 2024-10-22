package com.mtvs.devlinkbackend.reply.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyUpdateRequestDTO {
    private Long replyId;
    private String content;
}
