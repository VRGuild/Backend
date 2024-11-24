package com.mtvs.devlinkbackend.channel.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ChannelRegistRequestDTO {
    private List<PositionType> positionTypes;
}