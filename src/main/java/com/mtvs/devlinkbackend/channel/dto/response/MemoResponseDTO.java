package com.mtvs.devlinkbackend.channel.dto.response;

import com.mtvs.devlinkbackend.channel.entity.MemoInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemoResponseDTO {
    private List<MemoInfo> memoInfoList;
}
