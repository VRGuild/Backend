package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.response.PdfResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.PdfInfo;
import com.mtvs.devlinkbackend.channel.repository.PdfViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PdfViewService {
    @Autowired
    private PdfViewRepository pdfViewRepository;

    @Transactional
    public PdfResponseDTO getPdfUrl(String channelId) {
        try {
            PdfResponseDTO pdfResponseDTO = new PdfResponseDTO();
            List<PdfInfo> pdfInfoList = pdfViewRepository.findAllByChannelId(channelId);
            pdfResponseDTO.setPdfUrlList(pdfInfoList);
            return pdfResponseDTO;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

}
