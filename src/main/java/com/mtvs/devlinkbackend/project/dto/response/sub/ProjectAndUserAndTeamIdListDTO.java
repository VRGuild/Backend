package com.mtvs.devlinkbackend.project.dto.response.sub;

import com.mtvs.devlinkbackend.project.entity.Project;
import com.mtvs.devlinkbackend.user.query.model.dto.response.sub.UserIdAndBusinessAndNicknameDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectAndUserAndTeamIdListDTO {
    private Project projectInfo;
    private UserIdAndBusinessAndNicknameDTO userInfo;
    private List<Long> supportTeamIdList;
}
