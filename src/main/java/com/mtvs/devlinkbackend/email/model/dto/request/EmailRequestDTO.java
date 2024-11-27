package com.mtvs.devlinkbackend.email.model.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailRequestDTO {
    private String email;
    private String text;
}
