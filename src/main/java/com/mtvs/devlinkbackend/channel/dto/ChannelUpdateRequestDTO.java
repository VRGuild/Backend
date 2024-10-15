package com.mtvs.devlinkbackend.channel.dto;

import com.mtvs.devlinkbackend.channel.entity.PositionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ChannelUpdateRequestDTO {
    private String channelId;
    private List<PositionType> positionTypes;
}