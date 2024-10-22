package com.mtvs.devlinkbackend.reply.dto.response;

import com.mtvs.devlinkbackend.reply.entity.Reply;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyListResponseDTO {
    private List<Reply> data;
}
