package com.mtvs.devlinkbackend.oauth2.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessRequestDTO {
    private Long characterId;
    private String nickname;
    private String businessName;
    private MultipartFile businessLogoImg;
    private String managerName;
    private String managerPhone;
}

