package com.mtvs.devlinkbackend.user.command.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DevInfoRequestDTO {
    private String devName;
    private String devEmail;
    private String devPhone;
    private String githubLink;
    private List<String> portfolioList;
    private String career;
    private List<String> categoryNameList;
    private List<String> tag;
    private String hope;
}