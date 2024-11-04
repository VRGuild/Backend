package com.mtvs.devlinkbackend.user.query.model.dto.response.sub;

import com.mtvs.devlinkbackend.user.query.repository.projection.SkillCategory_CategoryNameAndPointAvg;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DevInfoDTO {
    private List<SkillCategory_CategoryNameAndPointAvg> skillCategoryList;
}
