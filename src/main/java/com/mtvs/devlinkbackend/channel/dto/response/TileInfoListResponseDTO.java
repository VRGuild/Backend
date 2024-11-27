package com.mtvs.devlinkbackend.channel.dto.response;

import com.mtvs.devlinkbackend.channel.entity.TileInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TileInfoListResponseDTO {
    private List<TileInfo> data;
}
