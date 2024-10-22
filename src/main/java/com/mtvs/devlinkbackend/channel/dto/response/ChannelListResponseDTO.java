package com.mtvs.devlinkbackend.channel.dto.response;

import com.mtvs.devlinkbackend.channel.entity.Channel;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChannelListResponseDTO {
    private List<Channel> data;
}
