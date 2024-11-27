package com.mtvs.devlinkbackend.channel.dto.response;

import com.mtvs.devlinkbackend.channel.entity.PdfInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PdfResponseDTO {
    List<PdfInfo> pdfUrlList;
}
