package com.mtvs.devlinkbackend.user.command.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessRequestDTO {
    private String nickname;
    private String businessName;
    private String businessLogoImg;
    private String managerName;
    private String managerPhone;
}

