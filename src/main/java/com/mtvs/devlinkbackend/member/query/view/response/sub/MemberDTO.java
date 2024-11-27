package com.mtvs.devlinkbackend.member.query.view.response.sub;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.common.util.converter.AcceptStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long memberId;
    private String type;
    private Long assigneesId;
    private Long userId;
    private String motive;
    private Long groupId;
    private Integer isAccepted;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
