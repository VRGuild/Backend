package com.mtvs.devlinkbackend.channel.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PdfModifyDTO {
    private String pdfId;
    private String pdfUrl;
}
