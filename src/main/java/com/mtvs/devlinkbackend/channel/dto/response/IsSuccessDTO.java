package com.mtvs.devlinkbackend.channel.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IsSuccessDTO {
    private boolean isSuccess;
    private String message;
}
