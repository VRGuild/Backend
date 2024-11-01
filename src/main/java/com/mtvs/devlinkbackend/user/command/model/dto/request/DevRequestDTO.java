package com.mtvs.devlinkbackend.user.command.model.dto.request;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DevRequestDTO {
    private Long characterId;
    private String nickname;
    private String devName;
    private String devEmail;
    private String devPhone;
    private String githubLink;
    private List<String> portfolioList;
    private String career;
    private List<String> categoryNameList;
    private String tag;
    private String hope;
}