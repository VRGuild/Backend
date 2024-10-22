package com.mtvs.devlinkbackend.reply.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyRegistRequestDTO {
    private String content;
    private Long questionId;
}
