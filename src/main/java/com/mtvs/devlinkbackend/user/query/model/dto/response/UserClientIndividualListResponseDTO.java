package com.mtvs.devlinkbackend.user.query.model.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserClientIndividualListResponseDTO {
    private List<UserClientIndividual> data;
}
