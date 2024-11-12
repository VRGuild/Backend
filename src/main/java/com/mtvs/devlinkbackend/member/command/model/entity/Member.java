package com.mtvs.devlinkbackend.member.command.model.entity;

import com.mtvs.devlinkbackend.common.model.AcceptStatus;
import com.mtvs.devlinkbackend.common.util.converter.AcceptStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "MEMBER")
@Entity(name = "Member")
@Getter
@NoArgsConstructor
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "ASSIGNEES_ID")
    private Long assigneesId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "MOTIVE")
    private String motive;

    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "IS_ACCEPTED")
    @Convert(converter = AcceptStatusConverter.class)
    private AcceptStatus isAccepted;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public Member(String type, Long assigneesId, Long userId, String motive, Long groupId, AcceptStatus isAccepted) {
        this.type = type;
        this.assigneesId = assigneesId;
        this.userId = userId;
        this.groupId = groupId;
        this.motive = motive;
        this.isAccepted = isAccepted;
    }

    public void setIsAccepted(AcceptStatus isAccepted) {
        this.isAccepted = isAccepted;
    }
}
