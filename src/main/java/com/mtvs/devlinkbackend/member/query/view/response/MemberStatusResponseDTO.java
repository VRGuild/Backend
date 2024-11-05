package com.mtvs.devlinkbackend.member.query.view.response;

import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberStatusResponseDTO {
    private Member data;
}
