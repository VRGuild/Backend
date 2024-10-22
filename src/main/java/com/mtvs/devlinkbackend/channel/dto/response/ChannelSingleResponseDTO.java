package com.mtvs.devlinkbackend.channel.dto.response;

import com.mtvs.devlinkbackend.channel.entity.Channel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChannelSingleResponseDTO {
    private Channel data;
}
