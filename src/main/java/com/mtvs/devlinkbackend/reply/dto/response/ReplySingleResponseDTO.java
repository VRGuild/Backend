package com.mtvs.devlinkbackend.reply.dto.response;

import com.mtvs.devlinkbackend.reply.entity.Reply;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplySingleResponseDTO {
    private Reply data;
}
