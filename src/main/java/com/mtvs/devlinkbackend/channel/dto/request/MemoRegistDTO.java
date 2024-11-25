package com.mtvs.devlinkbackend.channel.dto.request;

import com.mtvs.devlinkbackend.channel.entity.Position;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemoRegistDTO {
    private String memoText;
    private Position position;
}
