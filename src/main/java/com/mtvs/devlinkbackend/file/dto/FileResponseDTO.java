package com.mtvs.devlinkbackend.file.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileResponseDTO {
    private List<String> fileURLs;
    private String message;
}
