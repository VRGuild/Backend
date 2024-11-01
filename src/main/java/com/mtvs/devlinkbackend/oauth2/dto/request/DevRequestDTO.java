package com.mtvs.devlinkbackend.oauth2.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mtvs.devlinkbackend.oauth2.entity.CategoryInfo;
import com.mtvs.devlinkbackend.util.StringListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.OneToMany;
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